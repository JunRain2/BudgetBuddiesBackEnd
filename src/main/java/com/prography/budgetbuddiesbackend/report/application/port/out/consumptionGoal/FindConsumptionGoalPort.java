package com.prography.budgetbuddiesbackend.report.application.port.out.consumptionGoal;

import java.time.LocalDate;
import java.util.List;

import com.prography.budgetbuddiesbackend.report.domain.ConsumptionGoal;

public interface FindConsumptionGoalPort {
	List<ConsumptionGoal> findMonthlyUserConsumptionGoalList(LocalDate goalAt, Long userId);
}
