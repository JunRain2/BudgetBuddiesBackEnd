package com.prography.budgetbuddiesbackend.report.application.port.in.expense;

import java.time.LocalDate;

public record RegisterExpenseCommand(
	int amount,
	Long userId,
	Long categoryId,
	String description,
	LocalDate expenseAt
) {
}
