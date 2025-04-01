package com.prography.budgetbuddiesbackend.report.application.port.in.expense;

import java.time.LocalDate;

public record UpdateExpenseCommand(
	Long expenseId,
	Long categoryId,
	LocalDate expenseAt
) {
}
