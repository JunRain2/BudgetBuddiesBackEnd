package com.prography.budgetbuddiesbackend.user.application;

import org.springframework.stereotype.Service;

import com.prography.budgetbuddiesbackend.user.application.port.in.RegisterUserCommand;
import com.prography.budgetbuddiesbackend.user.application.port.in.UserUseCase;
import com.prography.budgetbuddiesbackend.user.application.port.out.CreateUserPort;
import com.prography.budgetbuddiesbackend.user.application.port.out.RegisterUserDefaultCategoryConsumptionGoalPort;
import com.prography.budgetbuddiesbackend.user.application.port.out.UserCommand;
import com.prography.budgetbuddiesbackend.user.domain.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements UserUseCase {
	private final RegisterUserDefaultCategoryConsumptionGoalPort registerCategoryPort;
	private final CreateUserPort createUserPort;

	@Override
	public void registerUser(RegisterUserCommand command) {
		User user = createUserPort.createUser(new UserCommand());

		registerCategoryPort.registerUserDefaultCategoryConsumptionGoal(user.getId());
	}
}
