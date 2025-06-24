package com.prography.budgetbuddiesbackend.report.domain.expense.repository;

import com.prography.budgetbuddiesbackend.report.domain.category.entity.Category;
import com.prography.budgetbuddiesbackend.report.domain.expense.entity.Expense;
import com.prography.budgetbuddiesbackend.report.domain.expense.entity.ExpenseId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ExpenseRepository extends JpaRepository<Expense, ExpenseId>,
    ExpenseQueryRepository {

    @Modifying
    @Query("UPDATE Expense e SET e.category = :uncategorized WHERE e.category = :deletedCategory")
    void clearCategoryReference(@Param("deletedCategory") Category deletedCategory,
        @Param("uncategorized") Category uncategorized);

}
