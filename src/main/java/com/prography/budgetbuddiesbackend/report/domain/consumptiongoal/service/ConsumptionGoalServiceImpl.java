package com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.service;

import java.time.YearMonth;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.entity.ConsumptionGoal;
import com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.exception.NotFoundConsumptionGoalException;
import com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.repository.ConsumptionGoalRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ConsumptionGoalServiceImpl implements ConsumptionGoalService {
	private final ConsumptionGoalRepository consumptionGoalRepository;

	@Override
	public ConsumptionGoal save(ConsumptionGoal goal) {
		return consumptionGoalRepository.save(goal);
	}

	@Override
	public List<ConsumptionGoal> getByUserAndYearMonth(Long userId, YearMonth yearMonth) {
		if (yearMonth.isAfter(YearMonth.now())) {
			throw new NotFoundConsumptionGoalException();
		}

		return consumptionGoalRepository.findByUserIdAndGoalMonthWithCategory(userId, yearMonth);
	}
} 