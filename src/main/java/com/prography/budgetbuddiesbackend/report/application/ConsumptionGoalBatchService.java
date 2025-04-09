package com.prography.budgetbuddiesbackend.report.application;

import org.springframework.stereotype.Service;

import com.prography.budgetbuddiesbackend.report.application.port.in.consumptionGoal.ConsumptionGoalBatchUseCase;
import com.prography.budgetbuddiesbackend.report.application.port.out.consumptionGoal.ConsumptionGoalMigrationBatchPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConsumptionGoalBatchService implements ConsumptionGoalBatchUseCase {

	private final ConsumptionGoalMigrationBatchPort consumptionGoalBatchPort;

	@Override
	public void startMigrationPrevToCurrent() {
		consumptionGoalBatchPort.startMigration();
	}
}
