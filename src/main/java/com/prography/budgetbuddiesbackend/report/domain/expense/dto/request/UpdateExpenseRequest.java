package com.prography.budgetbuddiesbackend.report.domain.expense.dto.request;

import java.time.LocalDate;

public record UpdateExpenseRequest(
	Long expenseId,
	Long categoryId,
	LocalDate expenseAt
) {
}
