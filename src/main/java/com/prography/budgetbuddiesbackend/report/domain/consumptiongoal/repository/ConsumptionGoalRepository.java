package com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.repository;

import java.time.YearMonth;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.prography.budgetbuddiesbackend.report.domain.category.entity.Category;
import com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.entity.ConsumptionGoal;
import com.prography.budgetbuddiesbackend.report.domain.user.entity.User;

public interface ConsumptionGoalRepository extends JpaRepository<ConsumptionGoal, Long> {
	List<ConsumptionGoal> findByCategory(Category category);

	@Query("""
		    SELECT cg 
		    FROM ConsumptionGoal cg 
		    JOIN FETCH cg.category c 
		    WHERE cg.user = :user 
		      AND cg.goalMonth = :goalMonth 
		      AND cg.deletedAt IS NULL
		""")
	List<ConsumptionGoal> findByUserAndGoalMonthWithCategory(@Param("user") User user,
		@Param("goalMonth") YearMonth goalMonth);
}
