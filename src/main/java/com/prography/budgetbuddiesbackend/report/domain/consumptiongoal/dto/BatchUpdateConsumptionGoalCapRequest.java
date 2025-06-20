package com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.dto;

import java.util.List;

public record BatchUpdateConsumptionGoalCapRequest(List<GoalCapUpdate> goals) {
    public record GoalCapUpdate(Long consumptionGoalId, Integer cap) {}
} 