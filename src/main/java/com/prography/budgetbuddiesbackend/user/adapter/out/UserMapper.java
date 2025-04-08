package com.prography.budgetbuddiesbackend.user.adapter.out;

import org.springframework.stereotype.Component;

import com.prography.budgetbuddiesbackend.user.domain.User;

@Component
public class UserMapper {
	public User entityToUser(UserEntity entity) {
		return new User(entity.getId());
	}
}
