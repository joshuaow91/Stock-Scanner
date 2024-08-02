package com.server.authentication;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TokenRepository extends JpaRepository<Token, Integer> {

    @Query(value = "select t from Token t where t.user.id = :id and (t.expired = false or t.revoked = false)", nativeQuery = true)
    List<Token> findAllValidTokenByUser(Long id);

    Optional<Token> findByToken(String token);

    @Query(value = "select t from Token t where t.expired = true or t.revoked = true", nativeQuery = true)
    List<Token> findExpiredTokens();
}