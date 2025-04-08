package com.prography.budgetbuddiesbackend.report.application.port.out.consumptionGoal;

import java.util.List;

import com.prography.budgetbuddiesbackend.report.domain.ConsumptionGoal;

public interface CreateConsumptionGoalPort {
	void createConsumptionGoal(Long userId, ConsumptionGoal consumptionGoal);

	void createConsumptionGoalList(Long userId, List<ConsumptionGoal> consumptionGoalList);
}
