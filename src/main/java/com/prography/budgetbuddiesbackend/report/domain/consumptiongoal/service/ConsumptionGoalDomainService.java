package com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prography.budgetbuddiesbackend.report.domain.category.entity.Category;
import com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.entity.ConsumptionGoal;
import com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.repository.ConsumptionGoalRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ConsumptionGoalDomainService {
	private final ConsumptionGoalRepository consumptionGoalRepository;

	public ConsumptionGoal save(ConsumptionGoal goal) {
		return consumptionGoalRepository.save(goal);
	}

	public void deleteAllByCategory(Category category) {
		List<ConsumptionGoal> goals = consumptionGoalRepository.findByCategory(category);
		consumptionGoalRepository.deleteAll(goals);
	}
} 