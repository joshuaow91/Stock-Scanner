package com.server.authentication.service;

import com.server.authentication.repository.TokenRepository;
import com.server.users.repository.UserRepository;
import com.server.users.entity.Users;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    public JwtAuthenticationFilter(JwtService jwtService, UserRepository userRepository, TokenRepository tokenRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        log.info("Entering JwtAuthenticationFilter");

        Cookie[] cookies = request.getCookies();
        String jwt = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("access_token".equals(cookie.getName())) {
                    jwt = cookie.getValue();
                    break;
                }
            }
        }

        if (jwt == null) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String userIdentifier = jwtService.extractUsername(jwt);
            log.info("Extracted user identifier from JWT: {}", userIdentifier);

            Optional<Users> optionalUser = userRepository.findByUsernameOrEmail(userIdentifier);
            if (optionalUser.isEmpty()) {
                filterChain.doFilter(request, response);
                return;
            }

            Users userDetails = optionalUser.get();
            log.info("User details found: {}", userDetails);

            var isTokenValid = tokenRepository.findByToken(jwt)
                    .map(t -> !t.isExpired() && !t.isRevoked())
                    .orElse(false);
            log.info("Is token valid: {}", isTokenValid);

            if (jwtService.isTokenValid(jwt, userDetails) && isTokenValid) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
                log.info("Authentication set in security context for user: {}", userIdentifier);
            } else {
                if (jwtService.isTokenExpired(jwt)) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("Access token has expired.");
                    return;
                }
            }

            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException ex) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } catch (Exception ex) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }
}
