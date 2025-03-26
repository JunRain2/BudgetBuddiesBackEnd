package com.prography.budgetbuddiesbackend.report.application.port.out.consumptionGoal;

public record CreateConsumptionGoalCommand(Long categoryId, Long userId, int cap) {
}
