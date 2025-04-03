package com.prography.budgetbuddiesbackend.report.application.port.out.expense;

import com.prography.budgetbuddiesbackend.report.domain.Expense;

public interface UpdateExpensePort {
	void updateExpense(Expense expense);
}
