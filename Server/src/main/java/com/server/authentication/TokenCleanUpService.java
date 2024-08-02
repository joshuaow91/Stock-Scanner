package com.server.authentication;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TokenCleanUpService {

    private final TokenRepository tokenRepository;

    public TokenCleanUpService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void cleanupExpiredTokens() {
        List<Token> expiredTokens = tokenRepository.findExpiredTokens();
        tokenRepository.deleteAll(expiredTokens);
    }
}

