package com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.service;

import java.time.YearMonth;
import java.util.Map;

public interface ConsumptionGoalExpenseService {
	Map<Long, Integer> getTotalSpentByUserCategory(Long userId, YearMonth yearMonth);
}
