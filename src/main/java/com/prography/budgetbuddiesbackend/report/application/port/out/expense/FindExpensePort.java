package com.prography.budgetbuddiesbackend.report.application.port.out.expense;

import java.time.LocalDate;
import java.util.List;

import com.prography.budgetbuddiesbackend.report.domain.Expense;

public interface FindExpensePort {
	List<Expense> findMonthlyExpense(Long userId, LocalDate expenseMonth);

	Expense findDetailExpenseResult(Long expenseId);

	Expense findExpense(Long expenseId);
}
