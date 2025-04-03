package com.prography.budgetbuddiesbackend.report.application.port.out.consumptionGoal;

import java.time.LocalDate;
import java.util.List;

import com.prography.budgetbuddiesbackend.report.application.port.in.consumptionGoal.QueryConsumptionGoalResult;

public interface FindMonthlyUserConsumptionGoalPort {
	List<QueryConsumptionGoalResult> findMonthlyUserConsumptionGoalList(LocalDate goalAt, Long userId);
}
