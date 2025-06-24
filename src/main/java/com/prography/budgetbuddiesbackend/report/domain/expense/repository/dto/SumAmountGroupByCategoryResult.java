package com.prography.budgetbuddiesbackend.report.domain.expense.repository.dto;

import com.prography.budgetbuddiesbackend.report.domain.category.entity.CategoryId;

public record SumAmountGroupByCategoryResult(
	CategoryId categoryId,
	Integer spendingMoney
) {
}
