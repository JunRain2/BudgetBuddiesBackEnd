package com.prography.budgetbuddiesbackend.report.domain.expense.service.command;

import com.prography.budgetbuddiesbackend.report.domain.category.entity.CategoryId;
import com.prography.budgetbuddiesbackend.report.domain.expense.entity.ExpenseId;
import java.time.LocalDate;

public record UpdateExpenseCommand (
    ExpenseId expenseId,
    CategoryId categoryId,
    LocalDate expenseAt
){

}
