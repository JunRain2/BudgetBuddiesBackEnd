package com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.service;

import com.prography.budgetbuddiesbackend.report.domain.category.entity.CategoryId;
import com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.controller.dto.UserConsumptionGoalResponse;
import com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.entity.ConsumptionGoal;
import com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.service.command.BatchUpdateCapCommand;
import com.prography.budgetbuddiesbackend.user.entity.UserId;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ConsumptionGoalFacadeService implements ConsumptionGoalUseCase {

  private final ConsumptionGoalService consumptionGoalService;
  private final ConsumptionGoalMapper mapper;

  private final ConsumptionGoalExpenseService expenseService;

  @Override
  public List<UserConsumptionGoalResponse> getUserConsumptionGoalsByMonth(UserId userId,
      YearMonth yearMonth) {

    List<ConsumptionGoal> goals = consumptionGoalService.getByUserAndYearMonth(userId, yearMonth);
    Map<CategoryId, Integer> totalSpentByCategory = expenseService.getTotalSpentByUserCategory(
        userId, yearMonth);

    return goals.stream().map(goal -> mergeWithSpending(goal, totalSpentByCategory)).toList();
  }

  private UserConsumptionGoalResponse mergeWithSpending(ConsumptionGoal goal,
      Map<CategoryId, Integer> spendingMap) {

    CategoryId categoryId = goal.getCategory().getId();
    int usedAmount = spendingMap.getOrDefault(categoryId, 0);

    return mapper.entityToUserConsumptionGoalResponse(goal, usedAmount);
  }

  @Override
  public void batchUpdateCapForThisMonth(UserId userId, BatchUpdateCapCommand command) {

    YearMonth now = YearMonth.now();
    List<ConsumptionGoal> consumptionGoals = consumptionGoalService.getByUserAndYearMonth(userId,
        now);

    Map<CategoryId, BatchUpdateCapCommand.UpdateCapCommand> requestMap = command.capCommandList()
        .stream()
        .collect(Collectors.toMap(BatchUpdateCapCommand.UpdateCapCommand::categoryId, req -> req));

    consumptionGoals.stream().filter(goal -> requestMap.containsKey(goal.getCategory().getId()))
        .forEach(goal -> {
          int cap = requestMap.get(goal.getCategory().getId()).cap();
          goal.canUpdate(userId);
          goal.update(cap);
        });
  }
}