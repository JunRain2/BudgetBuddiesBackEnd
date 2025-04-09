package com.prography.budgetbuddiesbackend.report.adapter.in.web.dto;

import java.time.LocalDate;

public record UpdateExpenseRequest(
	Long categoryId,
	LocalDate expenseAt
) {

}
