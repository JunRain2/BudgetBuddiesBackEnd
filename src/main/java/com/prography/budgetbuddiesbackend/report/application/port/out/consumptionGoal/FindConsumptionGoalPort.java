package com.prography.budgetbuddiesbackend.report.application.port.out.consumptionGoal;

import java.util.List;
import java.util.Map;

import com.prography.budgetbuddiesbackend.report.domain.ConsumptionGoal;

public interface FindConsumptionGoalPort {

	Map<Long, ConsumptionGoal> findThisMonthUserConsumptionGoalMap(Long userId, List<Long> consumptionGoalIdList);
}
