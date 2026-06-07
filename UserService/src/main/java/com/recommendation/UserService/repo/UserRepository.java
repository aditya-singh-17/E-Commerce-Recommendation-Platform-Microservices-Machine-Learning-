package com.recommendation.UserService.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.recommendation.UserService.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}