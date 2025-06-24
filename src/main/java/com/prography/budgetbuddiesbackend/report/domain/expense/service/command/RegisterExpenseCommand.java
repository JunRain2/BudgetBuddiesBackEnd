package com.prography.budgetbuddiesbackend.report.domain.expense.service.command;

import com.prography.budgetbuddiesbackend.report.domain.category.entity.CategoryId;
import java.time.LocalDate;

public record RegisterExpenseCommand(
    CategoryId categoryId,
    Integer amount,
    String description,
    LocalDate expenseAt
) {

}
