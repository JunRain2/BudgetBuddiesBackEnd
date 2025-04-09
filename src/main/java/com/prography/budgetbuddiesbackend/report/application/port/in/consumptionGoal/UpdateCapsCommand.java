package com.prography.budgetbuddiesbackend.report.application.port.in.consumptionGoal;

import java.util.List;

import com.prography.budgetbuddiesbackend.report.adapter.in.web.dto.UpdateCapRequest;

import lombok.Getter;

@Getter
public class UpdateCapsCommand {
	private final Long userId;
	private final List<UpdateCapCommand> updateCapCommands;

	public UpdateCapsCommand(Long userId, List<UpdateCapRequest> requests) {
		this.userId = userId;
		this.updateCapCommands = requests.stream().map(c -> new UpdateCapCommand(c.id(), c.cap())).toList();

	}

	public record UpdateCapCommand(Long consumptionGoalId, int cap) {
	}
}
