package com.prography.budgetbuddiesbackend.report.application;

import com.prography.budgetbuddiesbackend.report.domain.Expense;

public interface UpdateExpensePort {
	void updateExpense(Expense expense);
}
