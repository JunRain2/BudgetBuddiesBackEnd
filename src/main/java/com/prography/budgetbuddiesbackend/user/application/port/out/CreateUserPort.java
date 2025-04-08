package com.prography.budgetbuddiesbackend.user.application.port.out;

import com.prography.budgetbuddiesbackend.user.domain.User;

public interface CreateUserPort {
	User createUser(UserCommand userCommand);
}
