package com.prography.budgetbuddiesbackend.report.adapter.in.external;

import org.springframework.stereotype.Component;

import com.prography.budgetbuddiesbackend.report.application.port.in.consumptionGoal.RegisterDefaultCategoryConsumptionGoalUseCase;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ExternalToCategoryAdapter {

	private final RegisterDefaultCategoryConsumptionGoalUseCase registerDefaultCategoryConsumptionGoalUseCase;

	public void createUserDefaultCategoryList(Long userId) {
		registerDefaultCategoryConsumptionGoalUseCase.registerConsumptionGoal(userId);
	}
}
