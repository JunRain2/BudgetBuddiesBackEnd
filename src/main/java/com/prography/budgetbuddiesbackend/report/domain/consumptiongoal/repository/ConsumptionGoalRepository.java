package com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.repository;

import com.prography.budgetbuddiesbackend.report.domain.category.entity.Category;
import com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.entity.ConsumptionGoal;
import com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.entity.ConsumptionGoalId;
import com.prography.budgetbuddiesbackend.user.entity.UserId;
import java.time.YearMonth;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ConsumptionGoalRepository extends
    JpaRepository<ConsumptionGoal, ConsumptionGoalId> {

  List<ConsumptionGoal> findByCategory(Category category);

  @Query("""
          SELECT cg
          FROM ConsumptionGoal cg
          JOIN FETCH cg.category c
          WHERE cg.userId = :userId
            AND cg.goalMonth = :goalMonth 
            AND cg.deletedAt IS NULL
      """)
  List<ConsumptionGoal> findByUserIdAndGoalMonthWithCategory(@Param("userId") UserId userId,
      @Param("goalMonth") YearMonth goalMonth);
}
