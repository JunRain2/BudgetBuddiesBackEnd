package com.prography.budgetbuddiesbackend.report.domain.expense.repository;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.prography.budgetbuddiesbackend.common.RepositoryTest;
import com.prography.budgetbuddiesbackend.report.domain.category.entity.Category;
import com.prography.budgetbuddiesbackend.report.domain.category.repository.CategoryRepository;
import com.prography.budgetbuddiesbackend.report.domain.expense.entity.Expense;
import com.prography.budgetbuddiesbackend.report.domain.expense.repository.dto.SumAmountGroupByCategoryResponse;
import com.prography.budgetbuddiesbackend.report.domain.user.entity.User;
import com.prography.budgetbuddiesbackend.report.domain.user.repository.UserRepository;

@RepositoryTest
class ExpenseRepositoryTest {
	@Autowired
	ExpenseRepository expenseRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	CategoryRepository categoryRepository;

	@Test
	void 카테고리별_월별_합계_정상_및_경계_테스트() {
		// given
		User user = userRepository.save(User.of());
		Category catA = categoryRepository.save(Category.of(user, "A"));
		Category catB = categoryRepository.save(Category.of(user, "B"));

		// 2024-06, catA: 1000, catB: 없음
		expenseRepository.save(Expense.of(user, catA, 1000, "A지출", LocalDate.of(2024, 6, 10)));
		// 2024-07, catA: 2000
		expenseRepository.save(Expense.of(user, catA, 2000, "A지출", LocalDate.of(2024, 7, 1)));
		// 2024-06, catA: soft delete
		Expense deleted = Expense.of(user, catA, 9999, "삭제지출", LocalDate.of(2024, 6, 11));
		expenseRepository.delete(expenseRepository.save(deleted));
		YearMonth yearMonth = YearMonth.of(2024, 6);

		// when
		List<SumAmountGroupByCategoryResponse> list = expenseRepository.findSumAmountGroupedByCategoryIdAndUserIdAndYearMonth(
			user.getId(), yearMonth.atDay(1), yearMonth.atEndOfMonth());

		// then
		assertThat(list).anySatisfy(dto -> {
			if (dto.categoryId().equals(catA.getId())) {
				assertThat(dto.spendingMoney()).isEqualTo(1000);
			}
		});
		assertThat(list.stream().noneMatch(dto -> dto.categoryId().equals(catB.getId()))).isTrue(); // catB는 없음
	}

	@Test
	void 존재하지_않는_유저_조회시_빈_결과_반환() {
		// when
		YearMonth yearMonth = YearMonth.of(2024, 6);
		List<SumAmountGroupByCategoryResponse> list = expenseRepository.findSumAmountGroupedByCategoryIdAndUserIdAndYearMonth(
			-1L, yearMonth.atDay(1), yearMonth.atEndOfMonth()
		);

		// then
		assertThat(list).isEmpty();
	}

	@Test
	void 미래_월_조회시_빈_결과_반환() {
		// given
		User user = userRepository.save(User.of());
		Category catA = categoryRepository.save(Category.of(user, "A"));
		expenseRepository.save(Expense.of(user, catA, 1000, "A지출", LocalDate.now()));

		YearMonth future = YearMonth.now().plusMonths(1);

		// when
		List<SumAmountGroupByCategoryResponse> list = expenseRepository.findSumAmountGroupedByCategoryIdAndUserIdAndYearMonth(
			user.getId(),
			future.atDay(1),
			future.atEndOfMonth()
		);
		// then
		assertThat(list).isEmpty();
	}
} 