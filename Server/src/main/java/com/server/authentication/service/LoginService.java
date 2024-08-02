package com.server.authentication.service;

import com.server.authentication.dto.AuthenticationRequestDTO;
import com.server.authentication.dto.AuthenticationResponseDTO;
import com.server.users.repository.UserRepository;
import com.server.users.entity.Users;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class LoginService {

    private final UserRepository repository;
    private final TokenService tokenService;
    private final CookieService cookieService;
    private final AuthenticationManager authenticationManager;
    private static final Logger log = LoggerFactory.getLogger(LoginService.class);

    public LoginService(UserRepository repository, TokenService tokenService, CookieService cookieService, AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.tokenService = tokenService;
        this.cookieService = cookieService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO request, HttpServletResponse response) {
        Users user = findAndValidateUser(request.getUsername().toLowerCase(), request.getPassword());
        log.info("Successfully authenticated user with identifier: {}", user.getUsername());

        String jwtToken = tokenService.generateToken(user);
        String refreshToken = tokenService.generateRefreshToken(user);

        addCookiesToResponse(response, jwtToken, refreshToken);
        tokenService.revokeAllUserTokens(user);
        tokenService.saveUserToken(user, jwtToken);

        return buildAuthenticationResponse(jwtToken, refreshToken);
    }

    private Users findAndValidateUser(String identifier, String password) {
        log.info("Authenticating user with identifier: {}", identifier);

        Optional<Users> optionalUser = repository.findByUsernameOrEmail(identifier);
        if (optionalUser.isEmpty()) {
            log.error("User not found with identifier: {}", identifier);
            throw new UsernameNotFoundException("User not found");
        }

        Users user = optionalUser.get();

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), password)
        );

        return user;
    }

    private void addCookiesToResponse(HttpServletResponse response, String jwtToken, String refreshToken) {
        cookieService.addHttpOnlyCookie(response, "access_token", jwtToken);
        cookieService.addHttpOnlyCookie(response, "refresh_token", refreshToken);
    }

    private AuthenticationResponseDTO buildAuthenticationResponse(String jwtToken, String refreshToken) {
        return AuthenticationResponseDTO.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        tokenService.refreshToken(request, response);
    }
}
