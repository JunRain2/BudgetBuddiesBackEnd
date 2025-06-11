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
import com.prography.budgetbuddiesbackend.report.domain.user.entity.User;
import com.prography.budgetbuddiesbackend.report.domain.user.repository.UserRepository;

@RepositoryTest
class ConsumptionGoalRepositoryTest {
	@Autowired
	ConsumptionGoalRepository consumptionGoalRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	CategoryRepository categoryRepository;

	@Test
	void 사용자와_연월로_소비목표_정상_조회() {
		// given
		User user = userRepository.save(User.of());
		Category category = categoryRepository.save(Category.of(user, "식비"));
		YearMonth month = YearMonth.of(2024, 6);

		ConsumptionGoal goal = ConsumptionGoal.of(user, category, 10000, month);
		consumptionGoalRepository.save(goal);

		// when
		List<ConsumptionGoal> result = consumptionGoalRepository.findByUserAndGoalMonthWithCategory(user, month);

		// then
		assertThat(result).hasSize(1);
		assertThat(result.get(0).getCategory().getName()).isEqualTo("식비");
		assertThat(result.get(0).getCap()).isEqualTo(10000);
	}

	@Test
	void 삭제된_소비목표는_조회되지_않음() {
		// given
		User user = userRepository.save(User.of());
		Category category = categoryRepository.save(Category.of(user, "교통"));
		YearMonth month = YearMonth.of(2024, 6);

		ConsumptionGoal goal = ConsumptionGoal.of(user, category, 5000, month);
		goal = consumptionGoalRepository.save(goal);
		consumptionGoalRepository.delete(goal);

		// when
		List<ConsumptionGoal> result = consumptionGoalRepository.findByUserAndGoalMonthWithCategory(user, month);

		// then
		assertThat(result).isEmpty();
	}

}