package com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prography.budgetbuddiesbackend.report.domain.category.entity.Category;
import com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.entity.ConsumptionGoal;

public interface ConsumptionGoalRepository extends JpaRepository<ConsumptionGoal, Long> {
	List<ConsumptionGoal> findByCategory(Category category);
}
