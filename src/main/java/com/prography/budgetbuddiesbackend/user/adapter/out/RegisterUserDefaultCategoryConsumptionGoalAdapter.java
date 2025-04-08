package com.prography.budgetbuddiesbackend.user.adapter.out;

import org.springframework.stereotype.Repository;

import com.prography.budgetbuddiesbackend.report.application.port.in.consumptionGoal.RegisterDefaultCategoryConsumptionGoalUseCase;
import com.prography.budgetbuddiesbackend.user.application.port.out.RegisterUserDefaultCategoryConsumptionGoalPort;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
class RegisterUserDefaultCategoryConsumptionGoalAdapter
	implements RegisterUserDefaultCategoryConsumptionGoalPort {

	private final RegisterDefaultCategoryConsumptionGoalUseCase registerDefaultCategoryConsumptionGoalUseCase;

	public void registerUserDefaultCategoryConsumptionGoal(Long userId) {
		registerDefaultCategoryConsumptionGoalUseCase.registerConsumptionGoal(userId);
	}
}
