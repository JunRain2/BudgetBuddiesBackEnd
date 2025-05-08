package com.prography.budgetbuddiesbackend.report.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prography.budgetbuddiesbackend.report.domain.user.entity.User;

public interface JpaUserRepository extends JpaRepository<User, Long> {
}
