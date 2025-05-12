package com.prography.budgetbuddiesbackend.report.domain.expense.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.prography.budgetbuddiesbackend.report.domain.category.entity.Category;
import com.prography.budgetbuddiesbackend.report.domain.expense.entity.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
	@Modifying
	@Query("UPDATE Expense e SET e.category = NULL WHERE e.category =: category")
	void clearCategoryReference(@Param("category") Category category);
}
