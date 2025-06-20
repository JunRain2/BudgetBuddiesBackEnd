package com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.entity;

import static org.assertj.core.api.Assertions.*;

import java.time.YearMonth;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.prography.budgetbuddiesbackend.report.domain.category.entity.Category;

class ConsumptionGoalTest {

	@Test
	@DisplayName("userId로 소비 목표를 생성할 수 있다")
	void createConsumptionGoalWithUserId() {
		// given
		Long userId = 1L;
		Category category = Category.of(userId, "식비");
		Integer cap = 100000;
		YearMonth yearMonth = YearMonth.of(2024, 6);

		// when
		ConsumptionGoal goal = ConsumptionGoal.of(userId, category, cap, yearMonth);

		// then
		assertThat(goal.getUserId()).isEqualTo(userId);
		assertThat(goal.getCategory()).isEqualTo(category);
		assertThat(goal.getCap()).isEqualTo(cap);
		assertThat(goal.getGoalMonth()).isEqualTo(yearMonth);
	}

	@Test
	@DisplayName("소비 목표의 한도를 변경할 수 있다")
	void setCap() {
		// given
		Long userId = 1L;
		Category category = Category.of(userId, "식비");
		ConsumptionGoal goal = ConsumptionGoal.of(userId, category, 100000, YearMonth.of(2024, 6));
		Integer newCap = 150000;

		// when
		goal.update(newCap);

		// then
		assertThat(goal.getCap()).isEqualTo(newCap);
	}
} 