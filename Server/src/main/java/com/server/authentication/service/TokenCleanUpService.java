package com.server.authentication.service;

import com.server.authentication.entity.Token;
import com.server.authentication.repository.TokenRepository;
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

