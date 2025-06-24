package com.prography.budgetbuddiesbackend.report.domain.expense.service;

import com.prography.budgetbuddiesbackend.report.domain.expense.entity.ExpenseId;
import com.prography.budgetbuddiesbackend.report.domain.expense.service.command.RegisterExpenseCommand;
import com.prography.budgetbuddiesbackend.report.domain.expense.service.command.UpdateExpenseCommand;
import com.prography.budgetbuddiesbackend.user.entity.UserId;

public interface ExpenseUseCase {

    void registerExpense(RegisterExpenseCommand command, UserId userId);

    void updateExpense(UpdateExpenseCommand command, UserId userId);

    void deleteExpense(ExpenseId expenseId, UserId userId);
}
