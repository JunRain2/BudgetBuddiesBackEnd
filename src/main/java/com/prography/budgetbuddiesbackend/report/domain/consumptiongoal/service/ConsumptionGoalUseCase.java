package com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.service;

import java.time.YearMonth;
import java.util.List;

import com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.controller.dto.BatchUpdateConsumptionGoalCapRequest;
import com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.controller.dto.UserConsumptionGoalResponse;

public interface ConsumptionGoalUseCase {
	List<UserConsumptionGoalResponse> getUserConsumptionGoalsByMonth(Long userId, YearMonth yearMonth);

	void batchUpdateCap(Long userId, BatchUpdateConsumptionGoalCapRequest request);
}
