package com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.service;

import com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.controller.dto.UserConsumptionGoalResponse;
import com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.service.command.BatchUpdateCapCommand;
import com.prography.budgetbuddiesbackend.user.entity.UserId;
import java.time.YearMonth;
import java.util.List;

public interface ConsumptionGoalUseCase {

  List<UserConsumptionGoalResponse> getUserConsumptionGoalsByMonth(UserId userId,
      YearMonth yearMonth);

  void batchUpdateCapForThisMonth(UserId userId, BatchUpdateCapCommand request);
}
