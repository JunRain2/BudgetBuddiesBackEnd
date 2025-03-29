package com.prography.budgetbuddiesbackend.report.adapter.in;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserConsumptionGoalListResponse {
	private final List<UserConsumptionGoalResponse> consumptionGoalList;

	private record UserConsumptionGoalResponse(
		Long consumptionGoalId,
		LocalDate goalAt,
		int cap,
		int spendingAmount,
		int residualAmount
	) {
	}
}
