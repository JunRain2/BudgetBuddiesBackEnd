package com.prography.budgetbuddiesbackend.report.adapter.in;

import java.util.List;

import com.prography.budgetbuddiesbackend.report.application.port.in.consumptionGoal.QueryConsumptionGoalResult;

import lombok.Getter;

@Getter
public class UserMonthlyConsumptionGoalListResponse {
	private final List<UserConsumptionGoalResponse> consumptionGoalList;

	public UserMonthlyConsumptionGoalListResponse(List<QueryConsumptionGoalResult> results) {
		this.consumptionGoalList = results.stream().map(
			c -> new UserConsumptionGoalResponse(
				c.consumptionGoalId(),
				c.categoryName(),
				c.cap(),
				c.spendingMoney(),
				c.cap() - c.spendingMoney()
			)
		).toList();
	}

	private record UserConsumptionGoalResponse(
		Long id,
		String categoryName,
		int cap,
		int spendingMoney,
		int remainingAmount
	) { }
}
