package com.prography.budgetbuddiesbackend.report.domain.expense.repository;

import java.time.LocalDate;
import java.util.List;

import com.prography.budgetbuddiesbackend.report.domain.expense.repository.dto.SumAmountGroupByCategoryResult;

public interface ExpenseQueryRepository {
	List<SumAmountGroupByCategoryResult> findSumAmountGroupedByCategoryIdAndUserIdAndYearMonth(
		Long userId,
		LocalDate startDate,
		LocalDate endDate
	);
}
