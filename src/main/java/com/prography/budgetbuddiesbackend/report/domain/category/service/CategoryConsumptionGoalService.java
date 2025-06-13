package com.prography.budgetbuddiesbackend.report.domain.category.service;

import com.prography.budgetbuddiesbackend.report.domain.category.entity.Category;
import com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.entity.ConsumptionGoal;

public interface CategoryConsumptionGoalService {

	void save(ConsumptionGoal consumptionGoal);

	void deleteAllByCategory(Category category);
}
