package com.prography.budgetbuddiesbackend.report.domain.expense.repository;

import com.prography.budgetbuddiesbackend.user.entity.UserId;
import java.time.LocalDate;
import java.util.List;

import com.prography.budgetbuddiesbackend.report.domain.expense.repository.dto.SumAmountGroupByCategoryResult;

public interface ExpenseQueryRepository {
	List<SumAmountGroupByCategoryResult> findSumAmountGroupedByCategoryIdAndUserIdAndYearMonth(
		UserId userId,
		LocalDate startDate,
		LocalDate endDate
	);
}
