package com.prography.budgetbuddiesbackend.report.adapter.out.persistence;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

interface ExpenseRepository extends JpaRepository<ExpenseEntity, Long> {
	@Query("SELECT COALESCE(SUM(e.amount), 0) "
		+ "FROM ExpenseEntity e "
		+ "WHERE e.categoryId = :categoryId "
		+ "AND FUNCTION('YEAR', e.expenseAt) = FUNCTION('YEAR', :goalAt) "
		+ "AND FUNCTION('MONTH', e.expenseAt) = FUNCTION('MONTH', :goalAt)")
	Integer sumAmountByExpenseAtAndCategory(@Param("goalAt") LocalDate goalAt, @Param("categoryId") Long categoryId);
}
