package com.example.test.repository;

import com.example.test.domain.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, Long> {
    @Cacheable("userByUsername")
    Optional<User> findUserByUsername(String username);

    boolean existsByUsername(String username);
}