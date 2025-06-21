package com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.controller.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BatchUpdateConsumptionGoalCapRequest(@NotEmpty @Valid @Size(min = 1) List<GoalCapUpdate> goals) {
	public record GoalCapUpdate(@NotNull Long consumptionGoalId,
								@NotNull @Min(value = 1, message = "소비 금액은 1원 이상이어야 합니다.") Integer cap) {
	}
} 