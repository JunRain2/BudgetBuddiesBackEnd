package com.prography.budgetbuddiesbackend.report.application.port.out.consumptionGoal;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.prography.budgetbuddiesbackend.report.domain.ConsumptionGoal;

public interface FindConsumptionGoalPort {
	List<ConsumptionGoal> findMonthlyUserConsumptionGoalList(LocalDate goalAt, Long userId);

	Map<Long, ConsumptionGoal> findThisMonthUserConsumptionGoalMap(Long userId, List<Long> consumptionGoalIdList);
}
