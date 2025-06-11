package com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.dto;

public record UserConsumptionGoalResponse(
	Long consumptionGoalId,
	String categoryName,
	Integer cap,
	Integer totalSpent,
	Integer remainingAmount
) {
}
