package com.prography.budgetbuddiesbackend.report.adapter.in;

import java.time.LocalDate;

public record RegisterExpenseRequest(
	Long categoryId,
	int amount,
	String description,
	LocalDate expenseAt
) {
}
