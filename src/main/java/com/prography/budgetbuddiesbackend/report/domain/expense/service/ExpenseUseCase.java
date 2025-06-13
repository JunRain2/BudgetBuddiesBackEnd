package com.prography.budgetbuddiesbackend.report.domain.expense.service;

import com.prography.budgetbuddiesbackend.report.domain.expense.controller.dto.request.RegisterExpenseRequest;
import com.prography.budgetbuddiesbackend.report.domain.expense.controller.dto.request.UpdateExpenseRequest;

public interface ExpenseUseCase {
	void registerExpense(RegisterExpenseRequest request, Long userId);

	void updateExpense(UpdateExpenseRequest request, Long userId);

	void deleteExpense(Long expenseId, Long userId);
}
