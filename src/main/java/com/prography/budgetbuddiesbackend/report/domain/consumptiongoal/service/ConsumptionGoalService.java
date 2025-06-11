package com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.service;

import java.time.YearMonth;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.dto.UserConsumptionGoalResponse;
import com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.entity.ConsumptionGoal;
import com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.exception.NotFoundConsumptionGoalException;
import com.prography.budgetbuddiesbackend.report.domain.expense.service.ExpenseDomainService;
import com.prography.budgetbuddiesbackend.report.domain.user.entity.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ConsumptionGoalService {
	private final ConsumptionGoalDomainService consumptionGoalDomainService;
	private final ConsumptionGoalMapper mapper;

	private final ExpenseDomainService expenseService;

	/**
	 * 사용자, 연월 기준 소비목표와 카테고리별 월 소비합계 조회
	 */
	public List<UserConsumptionGoalResponse> getUserConsumptionGoalsByMonth(User user, YearMonth yearMonth) {
		if (yearMonth.isAfter(YearMonth.now())) {
			throw new NotFoundConsumptionGoalException();
		}

		List<ConsumptionGoal> goals = consumptionGoalDomainService.findAllByUserAndYearMonth(user, yearMonth);
		Map<Long, Integer> spendingMap = expenseService.getCategorySpendingByUserAndMonth(user, yearMonth);

		return goals.stream()
			.map(goal -> mapToResponse(goal, spendingMap))
			.toList();
	}

	private UserConsumptionGoalResponse mapToResponse(ConsumptionGoal goal, Map<Long, Integer> spendingMap) {
		Long categoryId = goal.getCategory().getId();
		int usedAmount = spendingMap.getOrDefault(categoryId, 0);

		return mapper.entityToUserConsumptionGoalResponse(goal, usedAmount);
	}
}