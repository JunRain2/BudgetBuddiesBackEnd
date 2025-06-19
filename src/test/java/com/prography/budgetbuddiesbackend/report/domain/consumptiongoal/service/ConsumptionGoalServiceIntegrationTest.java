package com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.service;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.prography.budgetbuddiesbackend.common.ServiceIntegrationTest;
import com.prography.budgetbuddiesbackend.report.domain.category.entity.Category;
import com.prography.budgetbuddiesbackend.report.domain.category.repository.CategoryRepository;
import com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.dto.UserConsumptionGoalResponse;
import com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.entity.ConsumptionGoal;
import com.prography.budgetbuddiesbackend.report.domain.expense.entity.Expense;
import com.prography.budgetbuddiesbackend.report.domain.expense.service.ExpenseServiceImpl;
import com.prography.budgetbuddiesbackend.report.domain.expense.repository.ExpenseRepository;

@ServiceIntegrationTest
class ConsumptionGoalServiceIntegrationTest {
	@Autowired
	ConsumptionGoalUseCase consumptionGoalService;
	@Autowired
	ConsumptionGoalService consumptionGoalDomainService;
	@Autowired
	CategoryRepository categoryRepository;
	@Autowired
	ExpenseServiceImpl expenseService;
	@Autowired
	ExpenseRepository expenseRepository;

	@Test
	void 사용자와_연월로_소비목표_조회_정상_및_경계() {
		// given
		Long userId = 1L;
		Category category = categoryRepository.save(Category.of(userId, "식비"));
		YearMonth month = YearMonth.of(2024, 6);

		ConsumptionGoal goal = ConsumptionGoal.of(userId, category, 10000, month);
		consumptionGoalDomainService.save(goal);

		// when
		List<UserConsumptionGoalResponse> result = consumptionGoalService.getUserConsumptionGoalsByMonth(userId, month);

		// then
		assertThat(result).hasSize(1);
		assertThat(result.get(0).categoryName()).isEqualTo("식비");
		assertThat(result.get(0).cap()).isEqualTo(10000);
	}

	@Test
	void 미래_연월_조회시_예외_발생() {
		// given
		Long userId = 1L;
		YearMonth futureMonth = YearMonth.now().plusMonths(1);

		// when & then
		assertThatThrownBy(() -> consumptionGoalService.getUserConsumptionGoalsByMonth(userId, futureMonth)).isInstanceOf(
			RuntimeException.class);
	}

	@Test
	void 소비목표_없을때_빈_리스트_반환() {
		// given
		Long userId = 1L;
		YearMonth month = YearMonth.of(2024, 6);

		// when
		List<UserConsumptionGoalResponse> result = consumptionGoalService.getUserConsumptionGoalsByMonth(userId, month);

		// then
		assertThat(result).isEmpty();
	}

	@Test
	void 소비금액이_있을_때_remainingAmount가_정확히_계산된다() {
		// given
		Long userId = 1L;
		Category cat = categoryRepository.save(Category.of(userId, "식비"));
		YearMonth month = YearMonth.of(2024, 6);
		consumptionGoalDomainService.save(ConsumptionGoal.of(userId, cat, 10000, month));

		Expense expense = expenseRepository.save(Expense.of(userId, cat, 3000, "점심", LocalDate.of(2024, 6, 15)));

		// when
		List<UserConsumptionGoalResponse> result = consumptionGoalService.getUserConsumptionGoalsByMonth(userId, month);

		// then
		assertThat(result).hasSize(1);
		assertThat(result.get(0).totalSpent()).isEqualTo(3000);
		assertThat(result.get(0).remainingAmount()).isEqualTo(7000);
	}

	@Test
	void 소비금액이_0원인_경우_remainingAmount는_cap과_같다() {
		// given
		Long userId = 1L;
		Category cat = categoryRepository.save(Category.of(userId, "식비"));
		YearMonth month = YearMonth.of(2024, 6);
		consumptionGoalDomainService.save(ConsumptionGoal.of(userId, cat, 10000, month));

		// when
		List<UserConsumptionGoalResponse> result = consumptionGoalService.getUserConsumptionGoalsByMonth(userId, month);

		// then
		assertThat(result).hasSize(1);
		assertThat(result.get(0).totalSpent()).isZero();
		assertThat(result.get(0).remainingAmount()).isEqualTo(10000);
	}

	@Test
	void 여러_유저가_있을때_본인것만_조회된다() {
		// given
		Long userId1 = 1L;
		Long userId2 = 2L;
		Category cat1 = categoryRepository.save(Category.of(userId1, "식비"));
		Category cat2 = categoryRepository.save(Category.of(userId2, "교통"));
		YearMonth month = YearMonth.of(2024, 6);
		consumptionGoalDomainService.save(ConsumptionGoal.of(userId1, cat1, 10000, month));
		consumptionGoalDomainService.save(ConsumptionGoal.of(userId2, cat2, 20000, month));

		// when
		List<UserConsumptionGoalResponse> result1 = consumptionGoalService.getUserConsumptionGoalsByMonth(userId1, month);
		List<UserConsumptionGoalResponse> result2 = consumptionGoalService.getUserConsumptionGoalsByMonth(userId2, month);

		// then
		assertThat(result1).hasSize(1);
		assertThat(result1.get(0).categoryName()).isEqualTo("식비");
		assertThat(result2).hasSize(1);
		assertThat(result2.get(0).categoryName()).isEqualTo("교통");
	}
}