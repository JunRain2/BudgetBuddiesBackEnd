package com.prography.budgetbuddiesbackend.report.application.port.in.consumptionGoal;

import java.time.LocalDate;

import com.prography.budgetbuddiesbackend.report.adapter.in.UserConsumptionGoalListResponse;

public interface ConsumptionGoalUseCase {
	UserConsumptionGoalListResponse findMonthlyUserConsumptionGoal(LocalDate localDate, Long userId);

	void updateMultipleThisMonthCaps(UpdateCapsCommand command);
}
