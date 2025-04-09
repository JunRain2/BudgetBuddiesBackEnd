package com.prography.budgetbuddiesbackend.user.adapter.out;

import org.springframework.stereotype.Repository;

import com.prography.budgetbuddiesbackend.report.adapter.in.external.ExternalToConsumptionGoalAdapter;
import com.prography.budgetbuddiesbackend.user.application.port.out.RegisterUserCategoryConsumptionGoalPort;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
class RegisterUserCategoryConsumptionGoalAdapter
	implements RegisterUserCategoryConsumptionGoalPort {

	private final ExternalToConsumptionGoalAdapter externalToConsumptionGoalAdapter;

	public void registerUserCategoryConsumptionGoal(Long userId) {
		externalToConsumptionGoalAdapter.createUserDefaultCategoryList(userId);
	}
}
