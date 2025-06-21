package com.prography.budgetbuddiesbackend.report.domain.expense.controller.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

public record UpdateExpenseRequest(
	@NotNull Long expenseId,
	@NotNull Long categoryId,
	@NotNull @PastOrPresent LocalDate expenseAt
) {
}
