package com.prography.budgetbuddiesbackend.report.application;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.prography.budgetbuddiesbackend.report.application.port.in.consumptionGoal.CommandConsumptionGoalUseCase;
import com.prography.budgetbuddiesbackend.report.application.port.in.consumptionGoal.UpdateCapsCommand;
import com.prography.budgetbuddiesbackend.report.application.port.out.consumptionGoal.FindConsumptionGoalPort;
import com.prography.budgetbuddiesbackend.report.application.port.out.consumptionGoal.UpdateConsumptionGoalPort;
import com.prography.budgetbuddiesbackend.report.domain.ConsumptionGoal;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
class ConsumptionGoalCommandService implements CommandConsumptionGoalUseCase {
	private final FindConsumptionGoalPort findConsumptionGoalPort;
	private final UpdateConsumptionGoalPort updateConsumptionGoalPort;

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
