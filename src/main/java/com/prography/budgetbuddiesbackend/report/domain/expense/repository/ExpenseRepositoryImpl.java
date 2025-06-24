package com.prography.budgetbuddiesbackend.report.domain.expense.repository;

import static com.prography.budgetbuddiesbackend.report.domain.expense.entity.QExpense.expense;

import com.prography.budgetbuddiesbackend.report.domain.expense.repository.dto.SumAmountGroupByCategoryResult;
import com.prography.budgetbuddiesbackend.user.entity.UserId;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ExpenseRepositoryImpl implements ExpenseQueryRepository {

  private final JPAQueryFactory queryFactory;

  @Override
  public List<SumAmountGroupByCategoryResult> findSumAmountGroupedByCategoryIdAndUserIdAndYearMonth(
      UserId userId, LocalDate startDate, LocalDate endDate) {

    return queryFactory.select(
            Projections.constructor(SumAmountGroupByCategoryResult.class, expense.category.id,
                expense.amount.sum())).from(expense)
        .where(expense.userId.eq(userId).and(expense.expenseAt.between(startDate, endDate)))
        .groupBy(expense.category.id).fetch();
  }
}
