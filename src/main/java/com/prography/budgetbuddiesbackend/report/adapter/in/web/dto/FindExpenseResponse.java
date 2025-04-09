package com.prography.budgetbuddiesbackend.report.adapter.in.web.dto;

import java.time.LocalDate;

public record FindExpenseResponse(
	Long id,
	String description,
	String categoryName,
	int amount,
	LocalDate expenseAt
) {
}
