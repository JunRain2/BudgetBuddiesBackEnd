package com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.service.command;

import com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.entity.ConsumptionGoalId;
import java.util.List;

public record BatchUpdateCapCommand(List<UpdateCapCommand> capCommandList) {

    public record UpdateCapCommand(ConsumptionGoalId consumptionGoalId, Integer cap) {

    }
}
