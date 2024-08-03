package com.server.users.repository;

import com.server.users.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {

    @Query(value = "SELECT * FROM users WHERE LOWER(username) = LOWER(:id) OR LOWER(email) = LOWER(:id)", nativeQuery = true)
    Optional<Users> findByUsernameOrEmail(@Param("id") String id);

    boolean existsByUsernameIgnoreCase(String username);

    Users findUsersById(Long userID);

}

