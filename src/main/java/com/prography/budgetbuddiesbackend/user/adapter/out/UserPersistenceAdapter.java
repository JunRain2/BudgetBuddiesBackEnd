package com.prography.budgetbuddiesbackend.user.adapter.out;

import org.springframework.stereotype.Repository;

import com.prography.budgetbuddiesbackend.user.application.port.out.CreateUserPort;
import com.prography.budgetbuddiesbackend.user.application.port.out.UserCommand;
import com.prography.budgetbuddiesbackend.user.domain.User;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserPersistenceAdapter implements CreateUserPort {

	private final UserRepository userRepository;
	private final UserMapper mapper;

	@Override
	public User createUser(UserCommand userCommand) {
		UserEntity userEntity = userRepository.save(new UserEntity());

		return mapper.entityToUser(userEntity);
	}
}
