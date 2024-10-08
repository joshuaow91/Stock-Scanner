package com.server.authentication.service;

import com.server.authentication.dto.AuthenticationResponseDTO;
import com.server.authentication.dto.RegisterRequestDTO;
import com.server.users.dto.UserDTO;
import com.server.users.entity.Users;
import com.server.users.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final TokenService tokenService;
    private final CookieService cookieService;

    public RegistrationService(UserRepository repository, PasswordEncoder passwordEncoder, JwtService jwtService, TokenService tokenService, CookieService cookieService) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.tokenService = tokenService;
        this.cookieService = cookieService;
    }

    public AuthenticationResponseDTO register(RegisterRequestDTO request, HttpServletResponse response) {
        validateUsername(request.getUsername());

        Users user = buildUser(request);
        Users savedUser = saveUser(user);

        String jwtToken = jwtService.generateToken(savedUser);
        String refreshToken = jwtService.generateRefreshToken(savedUser);

        tokenService.saveUserToken(savedUser, jwtToken);

        cookieService.addHttpOnlyCookie(response, "access_token", jwtToken);
        cookieService.addHttpOnlyCookie(response, "refresh_token", refreshToken);

        return buildAuthenticationResponse(jwtToken, refreshToken, savedUser);
    }

    private void validateUsername(String username) {
        if (repository.existsByUsernameIgnoreCase(username)) {
            throw new DataIntegrityViolationException("Username is already taken!");
        }
    }

    private Users buildUser(RegisterRequestDTO request) {
        Users user = new Users();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail().toLowerCase());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        return user;
    }

    private Users saveUser(Users user) {
        try {
            return repository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Registration failed.", e);
        }
    }

    private AuthenticationResponseDTO buildAuthenticationResponse(String jwtToken, String refreshToken, Users user) {
        UserDTO userResponseDTO = new UserDTO(user.getId(), user.getUsername(), user.getEmail());
        return new AuthenticationResponseDTO(jwtToken, refreshToken, userResponseDTO);
    }

}
