package com.prography.budgetbuddiesbackend.report.domain.expense.repository;

import static com.prography.budgetbuddiesbackend.report.domain.expense.entity.QExpense.*;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.prography.budgetbuddiesbackend.report.domain.expense.repository.dto.SumAmountGroupByCategoryResult;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ExpenseRepositoryImpl implements ExpenseQueryRepository {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<SumAmountGroupByCategoryResult> findSumAmountGroupedByCategoryIdAndUserIdAndYearMonth(Long userId,
		LocalDate startDate, LocalDate endDate) {

		return queryFactory.select(
				Projections.constructor(SumAmountGroupByCategoryResult.class, expense.category.id, expense.amount.sum()))
			.from(expense)
			.where(expense.userId.eq(userId).and(expense.expenseAt.between(startDate, endDate)))
			.groupBy(expense.category.id)
			.fetch();
	}
}
