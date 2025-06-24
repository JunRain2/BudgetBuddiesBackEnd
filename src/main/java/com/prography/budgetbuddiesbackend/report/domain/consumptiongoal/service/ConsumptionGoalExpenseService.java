package com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.service;

import com.prography.budgetbuddiesbackend.report.domain.category.entity.CategoryId;
import com.prography.budgetbuddiesbackend.user.entity.UserId;
import java.time.YearMonth;
import java.util.Map;

public interface ConsumptionGoalExpenseService {
	Map<CategoryId, Integer> getTotalSpentByUserCategory(UserId userId, YearMonth yearMonth);
}
