package com.prography.budgetbuddiesbackend.report.adapter.in.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.prography.budgetbuddiesbackend.report.application.port.in.consumptionGoal.ConsumptionGoalBatchUseCase;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
class ConsumptionGoalScheduler {

	private final ConsumptionGoalBatchUseCase consumptionGoalBatchUseCase;

	@Scheduled(cron = "0 0 0 1 * *")
	public void startMigration() {
		consumptionGoalBatchUseCase.startMigrationPrevToCurrent();
	}


}
