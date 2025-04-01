package com.prography.budgetbuddiesbackend.report.application.port.out.expense;

import com.prography.budgetbuddiesbackend.report.domain.Expense;

public interface RegisterExpensePort {
	void registerExpense(Expense expense);
}
