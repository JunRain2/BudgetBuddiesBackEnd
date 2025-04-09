package com.prography.budgetbuddiesbackend.report.adapter.in.web.dto;

import java.time.LocalDate;

public record RegisterExpenseRequest(
	Long categoryId,
	int amount,
	String description,
	LocalDate expenseAt
) {
}
