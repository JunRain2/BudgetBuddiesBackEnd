package com.prography.budgetbuddiesbackend.user.adapter.out;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
