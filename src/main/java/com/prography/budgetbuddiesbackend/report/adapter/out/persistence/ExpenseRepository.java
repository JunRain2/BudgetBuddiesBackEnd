package com.prography.budgetbuddiesbackend.report.adapter.out.persistence;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.prography.budgetbuddiesbackend.report.domain.Expense;

public interface ExpenseRepository extends JpaRepository<ExpenseEntity, Long> {

	@Query(
		"SELECT e FROM ExpenseEntity e "
			+ "WHERE e.userId = :userId "
			+ "AND MONTH(e.expenseAt) = :expenseMonth "
			+ "AND YEAR(e.expenseAt) = :expenseYear"
	)
	List<ExpenseEntity> findByUserIdAndExpenseMonth(Long userId, int expenseYear, int expenseMonth);
}
