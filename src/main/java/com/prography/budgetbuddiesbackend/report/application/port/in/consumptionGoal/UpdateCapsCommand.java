package com.prography.budgetbuddiesbackend.report.application.port.in.consumptionGoal;

import java.util.List;

import lombok.Getter;

@Getter
public class UpdateCapsCommand {
	private final Long userId;
	private final List<UpdateCapCommand> updateCapCommands;

	public UpdateCapsCommand(Long userId, List<UpdateCapCommand> updateCapCommands) {
		this.userId = userId;
		this.updateCapCommands = updateCapCommands;
	}

	public record UpdateCapCommand(Long consumptionGoalId, int cap) {
	}
}
