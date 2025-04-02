package com.prography.budgetbuddiesbackend.report.application;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.prography.budgetbuddiesbackend.report.application.port.in.consumptionGoal.CommandConsumptionGoalUseCase;
import com.prography.budgetbuddiesbackend.report.application.port.in.consumptionGoal.QueryConsumptionGoalResult;
import com.prography.budgetbuddiesbackend.report.application.port.in.consumptionGoal.QueryConsumptionGoalUseCase;
import com.prography.budgetbuddiesbackend.report.application.port.in.consumptionGoal.UpdateCapsCommand;
import com.prography.budgetbuddiesbackend.report.application.port.out.consumptionGoal.FindConsumptionGoalPort;
import com.prography.budgetbuddiesbackend.report.application.port.out.consumptionGoal.UpdateConsumptionGoalPort;
import com.prography.budgetbuddiesbackend.report.domain.ConsumptionGoal;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
class ConsumptionGoalService implements QueryConsumptionGoalUseCase, CommandConsumptionGoalUseCase {
	private final FindConsumptionGoalPort findConsumptionGoalPort;
	private final UpdateConsumptionGoalPort updateConsumptionGoalPort;

	@Override
	public List<QueryConsumptionGoalResult> findMonthlyUserConsumptionGoal(LocalDate goalAt, Long userId) {
		return findConsumptionGoalPort.findMonthlyUserConsumptionGoalList(goalAt, userId);
	}

	@Override
	public void updateMultipleThisMonthCaps(UpdateCapsCommand command) {
		List<Long> consumptionGoalIdList = command.getUpdateCapCommands()
			.stream()
			.mapToLong(UpdateCapsCommand.UpdateCapCommand::consumptionGoalId)
			.boxed()
			.toList();

		Map<Long, ConsumptionGoal> goalMap = findConsumptionGoalPort
			.findThisMonthUserConsumptionGoalMap(command.getUserId(), consumptionGoalIdList);

		command.getUpdateCapCommands().forEach(c -> {
			ConsumptionGoal consumptionGoal = goalMap.get(c.consumptionGoalId());
			if (consumptionGoal != null) {
				consumptionGoal.modifyCap(c.cap());
			}
		});

		updateConsumptionGoalPort.updateConsumptionGoalListCap(goalMap.values().stream().toList());
	}
}
