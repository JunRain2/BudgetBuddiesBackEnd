package com.prography.budgetbuddiesbackend.report.application.port.out.consumptionGoal;

import java.util.List;

import com.prography.budgetbuddiesbackend.report.domain.ConsumptionGoal;

public interface UpdateConsumptionGoalPort {
	void updateConsumptionGoalList(List<ConsumptionGoal> consumptionGoalList);
}
