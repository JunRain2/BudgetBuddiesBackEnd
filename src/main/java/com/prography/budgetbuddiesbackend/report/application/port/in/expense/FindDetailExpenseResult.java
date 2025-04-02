package com.prography.budgetbuddiesbackend.report.application.port.in.expense;

import java.time.LocalDate;

public record FindDetailExpenseResult(
	Long expenseId,
	String categoryName,
	String description,
	int amount,
	LocalDate expenseAt
) {
}
