package com.prography.budgetbuddiesbackend.report.adapter.out.persistence;

import static com.prography.budgetbuddiesbackend.report.adapter.out.persistence.QCategoryEntity.*;
import static com.prography.budgetbuddiesbackend.report.adapter.out.persistence.QConsumptionGoalEntity.*;
import static com.prography.budgetbuddiesbackend.report.adapter.out.persistence.QExpenseEntity.*;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.prography.budgetbuddiesbackend.report.application.port.in.consumptionGoal.QueryConsumptionGoalResult;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ConsumptionGoalQueryRepository {

	private final JPAQueryFactory jpaQueryFactory;

	List<QueryConsumptionGoalResult> findMonthlyUserConsumptionGoalList(LocalDate goalMonth, Long userId) {
		int year = goalMonth.getYear();
		int month = goalMonth.getMonthValue();

		return jpaQueryFactory.select(
				Projections.constructor(QueryConsumptionGoalResult.class,
					consumptionGoalEntity.id,
					categoryEntity.name,
					consumptionGoalEntity.cap,
					expenseEntity.amount.sum().coalesce(0),
					consumptionGoalEntity.goalAt
				)
			).from(consumptionGoalEntity)
			.leftJoin(categoryEntity).on(consumptionGoalEntity.categoryId.eq(categoryEntity.id))
			.leftJoin(expenseEntity).on(
				expenseEntity.categoryId.coalesce(0L).eq(consumptionGoalEntity.categoryId),
				expenseEntity.expenseAt.month().eq(month),
				expenseEntity.expenseAt.year().eq(year)
			)
			.where(
				consumptionGoalEntity.userId.eq(userId),
				consumptionGoalEntity.goalAt.month().eq(month),
				consumptionGoalEntity.goalAt.year().eq(year)
			).groupBy(consumptionGoalEntity.id)
			.fetch();
	}
}
