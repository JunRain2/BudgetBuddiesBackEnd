package com.prography.budgetbuddiesbackend.report.domain.expense.controller.dto.request;

import java.time.LocalDate;

public record RegisterExpenseRequest(
	Long categoryId,
	Integer amount,
	String description,
	LocalDate expenseAt
) {
}
