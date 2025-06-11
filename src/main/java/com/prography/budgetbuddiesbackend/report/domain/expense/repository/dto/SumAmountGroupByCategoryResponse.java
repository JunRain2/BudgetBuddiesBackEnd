package com.prography.budgetbuddiesbackend.report.domain.expense.repository.dto;

public record SumAmountGroupByCategoryResponse(
	Long categoryId,
	Integer spendingMoney
) {
}
