package com.prography.budgetbuddiesbackend.report.application.port.in.consumptionGoal;

import java.time.LocalDate;

public record QueryConsumptionGoalResult(
	Long consumptionGoalId,
	String categoryName,
	int cap,
	int spendingMoney,
	LocalDate goalAt
	) {
}
