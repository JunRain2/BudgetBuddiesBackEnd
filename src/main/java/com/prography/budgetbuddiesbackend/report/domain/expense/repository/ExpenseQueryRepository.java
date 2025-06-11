package com.prography.budgetbuddiesbackend.report.domain.expense.repository;

import java.time.LocalDate;
import java.util.List;

import com.prography.budgetbuddiesbackend.report.domain.expense.repository.dto.SumAmountGroupByCategoryResponse;

public interface ExpenseQueryRepository {
	List<SumAmountGroupByCategoryResponse> findSumAmountGroupedByCategoryIdAndUserIdAndYearMonth(
		Long userId,
		LocalDate startDate,
		LocalDate endDate
	);
}
