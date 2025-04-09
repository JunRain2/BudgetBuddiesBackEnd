package com.prography.budgetbuddiesbackend.user.adapter.out;

import org.springframework.stereotype.Repository;

import com.prography.budgetbuddiesbackend.report.application.port.in.consumptionGoal.RegisterDefaultCategoryConsumptionGoalUseCase;
import com.prography.budgetbuddiesbackend.user.application.port.out.RegisterUserCategoryConsumptionGoalPort;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
class RegisterUserCategoryConsumptionGoalAdapter
	implements RegisterUserCategoryConsumptionGoalPort {

	private final RegisterDefaultCategoryConsumptionGoalUseCase registerDefaultCategoryConsumptionGoalUseCase;

	public void registerUserCategoryConsumptionGoal(Long userId) {
		registerDefaultCategoryConsumptionGoalUseCase.registerConsumptionGoal(userId);
	}
}
