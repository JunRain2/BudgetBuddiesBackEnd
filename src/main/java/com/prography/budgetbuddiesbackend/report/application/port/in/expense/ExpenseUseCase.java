package com.prography.budgetbuddiesbackend.report.application.port.in.expense;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseUseCase {
	void registerExpense(RegisterExpenseCommand command);

	List<FindMonthlyExpenseResult> findMonthlyExpenseList(Long userId, LocalDate expenseAt);

	FindDetailExpenseResult findDetailExpenseResult(Long expenseId);

	void updateExpense(UpdateExpenseCommand command);
}
