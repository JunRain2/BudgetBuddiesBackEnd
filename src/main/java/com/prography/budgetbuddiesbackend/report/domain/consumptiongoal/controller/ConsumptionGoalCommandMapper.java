package com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.controller;

import com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.controller.dto.BatchUpdateConsumptionGoalCapRequest;
import com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.entity.ConsumptionGoalId;
import com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.service.command.BatchUpdateCapCommand;
import com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.service.command.BatchUpdateCapCommand.UpdateCapCommand;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ConsumptionGoalCommandMapper {

    public BatchUpdateCapCommand requestToBatchUpdateCapCommand(
        BatchUpdateConsumptionGoalCapRequest request) {

        List<UpdateCapCommand> updateCapCommands =
            request.goals().stream().map(goal ->
                {
                    ConsumptionGoalId consumptionGoalKey = ConsumptionGoalId.of(
                        goal.consumptionGoalId());
                    return new UpdateCapCommand(consumptionGoalKey, goal.cap());
                }
            ).toList();

        return new BatchUpdateCapCommand(updateCapCommands);
    }
}
