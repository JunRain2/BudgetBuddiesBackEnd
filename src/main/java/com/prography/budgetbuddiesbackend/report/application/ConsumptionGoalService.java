package com.prography.budgetbuddiesbackend.report.application;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.prography.budgetbuddiesbackend.report.adapter.in.UserConsumptionGoalListResponse;
import com.prography.budgetbuddiesbackend.report.application.port.in.consumptionGoal.ConsumptionGoalUseCase;
import com.prography.budgetbuddiesbackend.report.application.port.in.consumptionGoal.UpdateCapsCommand;
import com.prography.budgetbuddiesbackend.report.application.port.out.consumptionGoal.FindConsumptionGoalPort;
import com.prography.budgetbuddiesbackend.report.application.port.out.consumptionGoal.UpdateConsumptionGoalPort;
import com.prography.budgetbuddiesbackend.report.domain.ConsumptionGoal;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConsumptionGoalService implements ConsumptionGoalUseCase {
	private final FindConsumptionGoalPort findConsumptionGoalPort;
	private final UpdateConsumptionGoalPort updateConsumptioGoalPort;

	@Override
	public UserConsumptionGoalListResponse findMonthlyUserConsumptionGoal(LocalDate goalAt, Long userId) {
		List<ConsumptionGoal> consumptionGoalList = findConsumptionGoalPort.findMonthlyUserConsumptionGoalList(goalAt,
			userId);

		return new UserConsumptionGoalListResponse(consumptionGoalList);
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

		updateConsumptioGoalPort.updateConsumptionGoalListCap(goalMap.values().stream().toList());
	}
}
