package com.prography.budgetbuddiesbackend.report.application;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.prography.budgetbuddiesbackend.report.application.port.in.consumptionGoal.ConsumptionGoalUseCase;
import com.prography.budgetbuddiesbackend.report.application.port.in.consumptionGoal.UserConsumptionGoalListResponse;
import com.prography.budgetbuddiesbackend.report.application.port.out.consumptionGoal.FindConsumptionGoalPort;
import com.prography.budgetbuddiesbackend.report.domain.ConsumptionGoal;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConsumptionGoalService implements ConsumptionGoalUseCase {
	private final FindConsumptionGoalPort findConsumptionGoalPort;

	@Override
	public UserConsumptionGoalListResponse findMonthlyUserConsumptionGoal(LocalDate goalAt, Long userId) {
		List<ConsumptionGoal> consumptionGoalList = findConsumptionGoalPort.findMonthlyUserConsumptionGoalList(goalAt,
			userId);

		return new UserConsumptionGoalListResponse(consumptionGoalList);
	}
}
