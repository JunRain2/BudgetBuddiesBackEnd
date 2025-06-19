package com.prography.budgetbuddiesbackend.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prography.budgetbuddiesbackend.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
} 