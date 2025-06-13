package com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.service;

import java.time.YearMonth;
import java.util.List;

import com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.entity.ConsumptionGoal;
import com.prography.budgetbuddiesbackend.report.domain.user.entity.User;

public interface ConsumptionGoalService {
	ConsumptionGoal save(ConsumptionGoal goal);

	List<ConsumptionGoal> getByUserAndYearMonth(User user, YearMonth yearMonth);
}
