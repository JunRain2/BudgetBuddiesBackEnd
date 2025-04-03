package com.prography.budgetbuddiesbackend.report.application;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.prography.budgetbuddiesbackend.report.application.port.in.consumptionGoal.QueryConsumptionGoalResult;
import com.prography.budgetbuddiesbackend.report.application.port.in.consumptionGoal.QueryConsumptionGoalUseCase;
import com.prography.budgetbuddiesbackend.report.application.port.out.consumptionGoal.FindConsumptionGoalPort;
import com.prography.budgetbuddiesbackend.report.application.port.out.consumptionGoal.FindMonthlyUserConsumptionGoalPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConsumptionGoalQueryService implements QueryConsumptionGoalUseCase {
	private final FindMonthlyUserConsumptionGoalPort findMonthlyUserConsumptionGoalPort;

	@Override
	public List<QueryConsumptionGoalResult> findMonthlyUserConsumptionGoal(LocalDate goalAt, Long userId) {
		return findMonthlyUserConsumptionGoalPort.findMonthlyUserConsumptionGoalList(goalAt, userId);
	}
}
