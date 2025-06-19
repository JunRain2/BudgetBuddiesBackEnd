package com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.service;

import java.time.YearMonth;
import java.util.List;

import com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.dto.UserConsumptionGoalResponse;

public interface ConsumptionGoalUseCase {
	List<UserConsumptionGoalResponse> getUserConsumptionGoalsByMonth(Long userId, YearMonth yearMonth);
}
