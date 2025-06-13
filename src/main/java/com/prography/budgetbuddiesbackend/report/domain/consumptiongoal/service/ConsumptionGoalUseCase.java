package com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.service;

import java.time.YearMonth;
import java.util.List;

import com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.dto.UserConsumptionGoalResponse;
import com.prography.budgetbuddiesbackend.report.domain.user.entity.User;

public interface ConsumptionGoalUseCase {
	List<UserConsumptionGoalResponse> getUserConsumptionGoalsByMonth(User user, YearMonth yearMonth);
}
