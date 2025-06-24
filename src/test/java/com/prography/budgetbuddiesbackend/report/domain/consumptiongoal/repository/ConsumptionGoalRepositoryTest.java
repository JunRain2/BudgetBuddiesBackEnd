package com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.repository;

import static org.assertj.core.api.Assertions.*;

import java.time.YearMonth;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.prography.budgetbuddiesbackend.common.RepositoryTest;
import com.prography.budgetbuddiesbackend.report.domain.category.entity.Category;
import com.prography.budgetbuddiesbackend.report.domain.category.repository.CategoryRepository;
import com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.entity.ConsumptionGoal;
import com.prography.budgetbuddiesbackend.user.entity.UserId;

@RepositoryTest
class ConsumptionGoalRepositoryTest {
	@Autowired
	ConsumptionGoalRepository consumptionGoalRepository;
	@Autowired
	CategoryRepository categoryRepository;

	@Test
	void 사용자와_연월로_소비목표_정상_조회() {
		// given
		UserId userId = UserId.generate();
		Category category = categoryRepository.save(Category.of(userId, "식비"));
		YearMonth month = YearMonth.of(2024, 6);

		ConsumptionGoal goal = ConsumptionGoal.of(userId, category, 10000, month);
		consumptionGoalRepository.save(goal);

		// when
		List<ConsumptionGoal> result = consumptionGoalRepository.findByUserIdAndGoalMonthWithCategory(userId, month);

		// then
		assertThat(result).hasSize(1);
		assertThat(result.get(0).getCategory().getName()).isEqualTo("식비");
		assertThat(result.get(0).getCap()).isEqualTo(10000);
	}

	@Test
	void 삭제된_소비목표는_조회되지_않음() {
		// given
		UserId userId = UserId.generate();
		Category category = categoryRepository.save(Category.of(userId, "교통"));
		YearMonth month = YearMonth.of(2024, 6);

		ConsumptionGoal goal = ConsumptionGoal.of(userId, category, 5000, month);
		goal = consumptionGoalRepository.save(goal);
		consumptionGoalRepository.delete(goal);

		// when
		List<ConsumptionGoal> result = consumptionGoalRepository.findByUserIdAndGoalMonthWithCategory(userId, month);

		// then
		assertThat(result).isEmpty();
	}

	@Test
	void 여러_id로_소비목표_일괄_조회() {
		// given
		UserId userId = UserId.generate();
		Category category1 = categoryRepository.save(Category.of(userId, "식비"));
		Category category2 = categoryRepository.save(Category.of(userId, "교통"));
		YearMonth month = YearMonth.of(2024, 6);

		ConsumptionGoal goal1 = consumptionGoalRepository.save(ConsumptionGoal.of(userId, category1, 10000, month));
		ConsumptionGoal goal2 = consumptionGoalRepository.save(ConsumptionGoal.of(userId, category2, 20000, month));

		// when
		List<ConsumptionGoal> result = consumptionGoalRepository.findAllById(List.of(goal1.getId(), goal2.getId()));

		// then
		assertThat(result).hasSize(2);
		assertThat(result).extracting("cap").containsExactlyInAnyOrder(10000, 20000);
	}
}