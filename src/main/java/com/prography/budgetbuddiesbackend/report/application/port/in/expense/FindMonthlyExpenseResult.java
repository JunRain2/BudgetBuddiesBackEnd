package com.prography.budgetbuddiesbackend.report.application.port.in.expense;

import java.time.LocalDate;

public record FindMonthlyExpenseResult(
	Long expenseId,
	LocalDate expenseAt,
	String description,
	int amount
) {
}
