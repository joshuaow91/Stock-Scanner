package com.server.authentication.service;

import com.server.authentication.repository.TokenRepository;
import com.server.authentication.entity.Token;
import com.server.authentication.enums.TokenTypeEnums;
import com.server.users.repository.UserRepository;
import com.server.users.entity.Users;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class TokenService {

    private final TokenRepository tokenRepository;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final CookieService cookieService;

    public TokenService(TokenRepository tokenRepository, JwtService jwtService, UserRepository userRepository, CookieService cookieService) {
        this.tokenRepository = tokenRepository;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.cookieService = cookieService;
    }

    public String generateToken(Users user) {
        return jwtService.generateToken(user);
    }

    public String generateRefreshToken(Users user) {
        return jwtService.generateRefreshToken(user);
    }

    public void saveUserToken(Users user, String jwtToken) {
        Token token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenTypeEnums.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    public void revokeAllUserTokens(Users user) {
        List<Token> validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty()) return;

        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });

        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String refreshToken = extractRefreshTokenFromCookies(request);
        if (refreshToken == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Refresh Token not found");
            return;
        }

        String userName = jwtService.extractUsername(refreshToken);
        if (userName == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Refresh Token");
            return;
        }

        Users user = getUser(userName);
        if (!jwtService.isTokenValid(refreshToken, user)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Refresh Token");
            return;
        }

        processTokens(response, user);
    }

    private Users getUser(String userName) {
        return userRepository.findByUsernameOrEmail(userName).orElseThrow();
    }

    private String extractRefreshTokenFromCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) return null;

        for (Cookie cookie : cookies) {
            if ("refresh_token".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }

    private void processTokens(HttpServletResponse response, Users user) throws IOException {
        String accessToken = jwtService.generateToken(user);
        String newRefreshToken = jwtService.generateRefreshToken(user);

        revokeAllUserTokens(user);
        saveUserToken(user, accessToken);

        cookieService.addHttpOnlyCookie(response, "access_token", accessToken);
        cookieService.addHttpOnlyCookie(response, "refresh_token", newRefreshToken);

        response.setStatus(HttpServletResponse.SC_OK);
    }
}
