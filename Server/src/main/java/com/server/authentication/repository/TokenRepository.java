package com.server.authentication.repository;

import java.util.List;
import java.util.Optional;

import com.server.authentication.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TokenRepository extends JpaRepository<Token, Integer> {

    @Query("SELECT t FROM Token t WHERE t.user.id = :userId AND (t.expired = false OR t.revoked = false)")
    List<Token> findAllValidTokenByUser(@Param("userId") Long userId);

    Optional<Token> findByToken(String token);

    @Query(value = "select t from Token t where t.expired = true or t.revoked = true", nativeQuery = true)
    List<Token> findExpiredTokens();
}