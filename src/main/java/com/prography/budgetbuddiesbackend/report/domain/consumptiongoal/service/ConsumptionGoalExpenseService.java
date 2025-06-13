package com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.service;

import java.time.YearMonth;
import java.util.Map;

import com.prography.budgetbuddiesbackend.report.domain.user.entity.User;

public interface ConsumptionGoalExpenseService {
	Map<Long, Integer> getTotalSpentByUserCategory(User user, YearMonth yearMonth);
}
