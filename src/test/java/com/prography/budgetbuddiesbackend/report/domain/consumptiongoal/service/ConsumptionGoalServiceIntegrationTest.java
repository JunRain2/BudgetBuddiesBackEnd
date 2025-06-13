package com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.service;

import static org.assertj.core.api.Assertions.*;

import java.time.YearMonth;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.prography.budgetbuddiesbackend.common.ServiceIntegrationTest;
import com.prography.budgetbuddiesbackend.report.domain.category.entity.Category;
import com.prography.budgetbuddiesbackend.report.domain.category.repository.CategoryRepository;
import com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.dto.UserConsumptionGoalResponse;
import com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.entity.ConsumptionGoal;
import com.prography.budgetbuddiesbackend.report.domain.user.entity.User;
import com.prography.budgetbuddiesbackend.report.domain.user.repository.UserRepository;
import com.prography.budgetbuddiesbackend.report.domain.expense.entity.Expense;
import com.prography.budgetbuddiesbackend.report.domain.expense.service.ExpenseServiceImpl;

@ServiceIntegrationTest
class ConsumptionGoalServiceIntegrationTest {
	@Autowired
	ConsumptionGoalFacadeService consumptionGoalService;
	@Autowired
	ConsumptionGoalServiceImpl consumptionGoalDomainService;
	@Autowired
	UserRepository userRepository;
	@Autowired
	CategoryRepository categoryRepository;
	@Autowired
	ExpenseServiceImpl expenseService;

	@Test
	void 사용자와_연월로_소비목표_조회_정상_및_경계() {
		// given
		User user = userRepository.save(User.of());
		Category category = categoryRepository.save(Category.of(user, "식비"));
		YearMonth month = YearMonth.of(2024, 6);

		ConsumptionGoal goal = ConsumptionGoal.of(user, category, 10000, month);
		consumptionGoalDomainService.save(goal);

		// when
		List<UserConsumptionGoalResponse> result = consumptionGoalService.getUserConsumptionGoalsByMonth(user, month);

		// then
		assertThat(result).hasSize(1);
		assertThat(result.get(0).categoryName()).isEqualTo("식비");
		assertThat(result.get(0).cap()).isEqualTo(10000);
	}

	@Test
	void 미래_연월_조회시_예외_발생() {
		// given
		User user = userRepository.save(User.of());
		YearMonth futureMonth = YearMonth.now().plusMonths(1);

		// when & then
		assertThatThrownBy(() -> consumptionGoalService.getUserConsumptionGoalsByMonth(user, futureMonth))
			.isInstanceOf(RuntimeException.class); // 실제 예외 타입에 맞게 수정
	}

	@Test
	void 소비목표_없을때_빈_리스트_반환() {
		// given
		User user = userRepository.save(User.of());
		YearMonth month = YearMonth.of(2024, 6);

		// when
		List<UserConsumptionGoalResponse> result = consumptionGoalService.getUserConsumptionGoalsByMonth(user, month);

		// then
		assertThat(result).isEmpty();
	}

	@Test
	void 여러_카테고리_여러_목표_정상_조회() {
		User user = userRepository.save(User.of());
		Category cat1 = categoryRepository.save(Category.of(user, "식비"));
		Category cat2 = categoryRepository.save(Category.of(user, "교통"));
		YearMonth month = YearMonth.of(2024, 6);
		consumptionGoalDomainService.save(ConsumptionGoal.of(user, cat1, 10000, month));
		consumptionGoalDomainService.save(ConsumptionGoal.of(user, cat2, 20000, month));

		List<UserConsumptionGoalResponse> result = consumptionGoalService.getUserConsumptionGoalsByMonth(user, month);

		assertThat(result).hasSize(2);
		assertThat(result.stream().map(UserConsumptionGoalResponse::categoryName)).containsExactlyInAnyOrder("식비", "교통");
	}

	@Test
	void 소비금액이_목표금액보다_클_경우_remainingAmount_음수() {
		User user = userRepository.save(User.of());
		Category cat = categoryRepository.save(Category.of(user, "식비"));
		YearMonth month = YearMonth.of(2024, 6);
		consumptionGoalDomainService.save(ConsumptionGoal.of(user, cat, 10000, month));
		// 소비 15000원
		Expense expense = Expense.of(user, cat, 15000, "초과지출", month.atDay(10));
		expenseService.save(expense);

		List<UserConsumptionGoalResponse> result = consumptionGoalService.getUserConsumptionGoalsByMonth(user, month);
		assertThat(result).hasSize(1);
		assertThat(result.get(0).remainingAmount()).isEqualTo(-5000);
	}

	@Test
	void 소비금액이_0원인_경우_remainingAmount는_cap과_같다() {
		User user = userRepository.save(User.of());
		Category cat = categoryRepository.save(Category.of(user, "식비"));
		YearMonth month = YearMonth.of(2024, 6);
		consumptionGoalDomainService.save(ConsumptionGoal.of(user, cat, 10000, month));

		List<UserConsumptionGoalResponse> result = consumptionGoalService.getUserConsumptionGoalsByMonth(user, month);
		assertThat(result).hasSize(1);
		assertThat(result.get(0).totalSpent()).isZero();
		assertThat(result.get(0).remainingAmount()).isEqualTo(10000);
	}

	@Test
	void 여러_유저가_있을때_본인것만_조회된다() {
		User user1 = userRepository.save(User.of());
		User user2 = userRepository.save(User.of());
		Category cat1 = categoryRepository.save(Category.of(user1, "식비"));
		Category cat2 = categoryRepository.save(Category.of(user2, "교통"));
		YearMonth month = YearMonth.of(2024, 6);
		consumptionGoalDomainService.save(ConsumptionGoal.of(user1, cat1, 10000, month));
		consumptionGoalDomainService.save(ConsumptionGoal.of(user2, cat2, 20000, month));

		List<UserConsumptionGoalResponse> result1 = consumptionGoalService.getUserConsumptionGoalsByMonth(user1, month);
		List<UserConsumptionGoalResponse> result2 = consumptionGoalService.getUserConsumptionGoalsByMonth(user2, month);

		assertThat(result1).hasSize(1);
		assertThat(result1.get(0).categoryName()).isEqualTo("식비");
		assertThat(result2).hasSize(1);
		assertThat(result2.get(0).categoryName()).isEqualTo("교통");
	}

	@Test
	void 카테고리별로_소비금액이_정확히_집계된다() {
		User user = userRepository.save(User.of());
		Category cat1 = categoryRepository.save(Category.of(user, "식비"));
		Category cat2 = categoryRepository.save(Category.of(user, "교통"));
		YearMonth month = YearMonth.of(2024, 6);
		consumptionGoalDomainService.save(ConsumptionGoal.of(user, cat1, 10000, month));
		consumptionGoalDomainService.save(ConsumptionGoal.of(user, cat2, 20000, month));
		// 식비 3000, 교통 5000
		expenseService.save(
			com.prography.budgetbuddiesbackend.report.domain.expense.entity.Expense.of(user, cat1, 3000, "식비지출", month.atDay(5)));
		expenseService.save(
			com.prography.budgetbuddiesbackend.report.domain.expense.entity.Expense.of(user, cat2, 5000, "교통지출", month.atDay(10)));

		List<UserConsumptionGoalResponse> result = consumptionGoalService.getUserConsumptionGoalsByMonth(user, month);
		assertThat(result).hasSize(2);
		UserConsumptionGoalResponse 식비 = result.stream().filter(r -> r.categoryName().equals("식비")).findFirst().orElseThrow();
		UserConsumptionGoalResponse 교통 = result.stream().filter(r -> r.categoryName().equals("교통")).findFirst().orElseThrow();
		assertThat(식비.totalSpent()).isEqualTo(3000);
		assertThat(교통.totalSpent()).isEqualTo(5000);
	}
}