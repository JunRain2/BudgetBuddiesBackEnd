package com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.service;

import org.springframework.stereotype.Component;

import com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.dto.UserConsumptionGoalResponse;
import com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.entity.ConsumptionGoal;

@Component
public class ConsumptionGoalMapper {
	public UserConsumptionGoalResponse entityToUserConsumptionGoalResponse(ConsumptionGoal consumptionGoal,
		int totalSpent) {
		return new UserConsumptionGoalResponse(
			consumptionGoal.getId(),
			consumptionGoal.getUser().getId(),
			consumptionGoal.getCategory().getName(),
			consumptionGoal.getCap(),
			totalSpent,
			consumptionGoal.getCap() - totalSpent
		);
	}

}
