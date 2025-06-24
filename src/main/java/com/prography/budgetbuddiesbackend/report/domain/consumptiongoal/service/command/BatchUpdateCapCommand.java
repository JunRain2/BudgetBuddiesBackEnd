package com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.service.command;

import com.prography.budgetbuddiesbackend.report.domain.category.entity.CategoryId;
import java.util.List;

public record BatchUpdateCapCommand(List<UpdateCapCommand> capCommandList) {

  public record UpdateCapCommand(CategoryId categoryId, Integer cap) {

  }
}
