package com.prography.budgetbuddiesbackend.report.adapter.in;

import java.time.LocalDate;

public record UpdateExpenseRequest(
	Long categoryId,
	LocalDate expenseAt
) {

}
