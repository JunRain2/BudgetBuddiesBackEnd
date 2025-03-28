package com.prography.budgetbuddiesbackend.report.adapter.in;

import java.time.LocalDate;
import java.util.List;

import com.prography.budgetbuddiesbackend.report.domain.ConsumptionGoal;

public class UserConsumptionGoalListResponse {
	private final List<UserConsumptionGoalResponse> consumptionGoalList;

	public UserConsumptionGoalListResponse(List<ConsumptionGoal> consumptionGoalList) {
		this.consumptionGoalList = consumptionGoalList.stream().map(c ->
			new UserConsumptionGoalResponse(
				c.getConsumptionGoalId(),
				c.getGoalAt(),
				c.getCap().value(),
				c.getSpendingMoney().value(),
				c.getResidualAmount()
			)
		).toList();
	}

	private record UserConsumptionGoalResponse(
		Long consumptionGoalId,
		LocalDate goalAt,
		int cap,
		int spendingAmount,
		int residualAmount
	) {
	}
}
