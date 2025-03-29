package com.prography.budgetbuddiesbackend.report.application.port.in.consumptionGoal;

import java.time.LocalDate;
import java.util.List;

public interface QueryConsumptionGoalUseCase {
	List<QueryConsumptionGoalResult> findMonthlyUserConsumptionGoal(LocalDate localDate, Long userId);
}
