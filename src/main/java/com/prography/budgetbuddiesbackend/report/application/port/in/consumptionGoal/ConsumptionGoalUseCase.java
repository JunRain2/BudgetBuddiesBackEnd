package com.prography.budgetbuddiesbackend.report.application.port.in.consumptionGoal;

import java.time.LocalDate;

public interface ConsumptionGoalUseCase {
	UserConsumptionGoalListResponse findMonthlyUserConsumptionGoal(LocalDate localDate, Long userId);
}
