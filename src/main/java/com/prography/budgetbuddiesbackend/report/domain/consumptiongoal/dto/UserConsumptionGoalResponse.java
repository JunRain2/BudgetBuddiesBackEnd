package com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.dto;

public record UserConsumptionGoalResponse(
	Long consumptionGoalId,
	Long userId,
	String CategoryName,
	Integer cap,
	Integer totalSpent,
	Integer remainingAmount
) {
}
