package com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.service;

import java.time.YearMonth;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.dto.UserConsumptionGoalResponse;
import com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.entity.ConsumptionGoal;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ConsumptionGoalFacadeService implements ConsumptionGoalUseCase {
	private final ConsumptionGoalService consumptionGoalService;
	private final ConsumptionGoalMapper mapper;

	private final ConsumptionGoalExpenseService expenseService;

	public List<UserConsumptionGoalResponse> getUserConsumptionGoalsByMonth(Long userId, YearMonth yearMonth) {
		List<ConsumptionGoal> goals = consumptionGoalService.getByUserAndYearMonth(userId, yearMonth);
		Map<Long, Integer> totalSpentByCategory = expenseService.getTotalSpentByUserCategory(userId, yearMonth);

		return goals.stream()
			.map(goal -> mergeWithSpending(goal, totalSpentByCategory))
			.toList();
	}

	private UserConsumptionGoalResponse mergeWithSpending(ConsumptionGoal goal, Map<Long, Integer> spendingMap) {
		Long categoryId = goal.getCategory().getId();
		int usedAmount = spendingMap.getOrDefault(categoryId, 0);

		return mapper.entityToUserConsumptionGoalResponse(goal, usedAmount);
	}
}