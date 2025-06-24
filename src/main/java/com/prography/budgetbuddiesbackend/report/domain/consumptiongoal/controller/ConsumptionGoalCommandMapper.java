package com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.controller;

import com.prography.budgetbuddiesbackend.report.domain.category.entity.CategoryId;
import com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.controller.dto.BatchUpdateConsumptionGoalCapRequest;
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
                    CategoryId categoryKey = CategoryId.of(goal.categoryId());
                    return new UpdateCapCommand(categoryKey, goal.cap());
                }
            ).toList();

        return new BatchUpdateCapCommand(updateCapCommands);
    }
}
