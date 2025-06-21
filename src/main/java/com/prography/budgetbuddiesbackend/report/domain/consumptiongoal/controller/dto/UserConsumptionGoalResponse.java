package com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserConsumptionGoalResponse(
	Long consumptionGoalId,
	String categoryName,
	Integer cap,
	Integer totalSpent
){
	@JsonProperty("remainingAmount")
	public Integer remainingAmount() {
		return cap - totalSpent;
	}
}
