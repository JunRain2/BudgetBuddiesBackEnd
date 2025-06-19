package com.prography.budgetbuddiesbackend.report.domain.expense.entity;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.prography.budgetbuddiesbackend.report.domain.category.entity.Category;
import com.prography.budgetbuddiesbackend.report.domain.expense.exception.NotRegisterExpenseException;
import com.prography.budgetbuddiesbackend.report.domain.expense.exception.NotUpdateExpenseException;

class ExpenseTest {

	@Test
	@DisplayName("userId로 지출을 생성할 수 있다")
	void createExpenseWithUserId() {
		// given
		Long userId = 1L;
		Category category = Category.of(userId, "식비");
		Integer amount = 10000;
		String description = "점심";
		LocalDate expenseAt = LocalDate.now().minusDays(1);

		// when
		Expense expense = Expense.of(userId, category, amount, description, expenseAt);

		// then
		assertThat(expense.getUserId()).isEqualTo(userId);
		assertThat(expense.getCategory()).isEqualTo(category);
		assertThat(expense.getAmount()).isEqualTo(amount);
		assertThat(expense.getDescription()).isEqualTo(description);
		assertThat(expense.getExpenseAt()).isEqualTo(expenseAt);
	}

	@Test
	@DisplayName("미래 날짜로 지출을 생성하면 예외가 발생한다")
	void createExpenseWithFutureDate_ThrowsException() {
		// given
		Long userId = 1L;
		Category category = Category.of(userId, "식비");
		LocalDate futureDate = LocalDate.now().plusDays(1);

		// when & then
		assertThatThrownBy(() -> Expense.of(userId, category, 10000, "점심", futureDate))
			.isInstanceOf(NotRegisterExpenseException.class);
	}

	@Test
	@DisplayName("0원 이하 금액으로 지출을 생성하면 예외가 발생한다")
	void createExpenseWithZeroAmount_ThrowsException() {
		// given
		Long userId = 1L;
		Category category = Category.of(userId, "식비");

		// when & then
		assertThatThrownBy(() -> Expense.of(userId, category, 0, "점심", LocalDate.now().minusDays(1)))
			.isInstanceOf(NotRegisterExpenseException.class);
	}

	@Test
	@DisplayName("지출을 수정할 수 있다")
	void updateExpense() {
		// given
		Long userId = 1L;
		Category originalCategory = Category.of(userId, "식비");
		Category newCategory = Category.of(userId, "교통");
		Expense expense = Expense.of(userId, originalCategory, 10000, "점심", LocalDate.now().minusDays(1));
		LocalDate newDate = LocalDate.now().minusDays(2);

		// when
		expense.update(newCategory, newDate);

		// then
		assertThat(expense.getCategory()).isEqualTo(newCategory);
		assertThat(expense.getExpenseAt()).isEqualTo(newDate);
	}

	@Test
	@DisplayName("지출 소유자 검증이 성공한다")
	void validateOwner_Success() {
		// given
		Long userId = 1L;
		Category category = Category.of(userId, "식비");
		Expense expense = Expense.of(userId, category, 10000, "점심", LocalDate.now().minusDays(1));

		// when & then
		assertThatCode(() -> expense.validateOwner(userId))
			.doesNotThrowAnyException();
	}

	@Test
	@DisplayName("다른 사용자가 지출을 수정하려고 하면 예외가 발생한다")
	void validateOwner_OtherUser_ThrowsException() {
		// given
		Long ownerId = 1L;
		Long otherUserId = 2L;
		Category category = Category.of(ownerId, "식비");
		Expense expense = Expense.of(ownerId, category, 10000, "점심", LocalDate.now().minusDays(1));

		// when & then
		assertThatThrownBy(() -> expense.validateOwner(otherUserId))
			.isInstanceOf(NotUpdateExpenseException.class);
	}

	@Test
	@DisplayName("지출 수정 가능 여부 검증이 성공한다")
	void validateModifiable_Success() {
		// given
		Long userId = 1L;
		Category category = Category.of(userId, "식비");
		Expense expense = Expense.of(userId, category, 10000, "점심", LocalDate.now().minusDays(1));
		LocalDate pastDate = LocalDate.now().minusDays(2);

		// when & then
		assertThatCode(() -> expense.validateModifiable(userId, pastDate))
			.doesNotThrowAnyException();
	}

	@Test
	@DisplayName("미래 날짜로 지출을 수정하려고 하면 예외가 발생한다")
	void validateModifiable_FutureDate_ThrowsException() {
		// given
		Long userId = 1L;
		Category category = Category.of(userId, "식비");
		Expense expense = Expense.of(userId, category, 10000, "점심", LocalDate.now().minusDays(1));
		LocalDate futureDate = LocalDate.now().plusDays(1);

		// when & then
		assertThatThrownBy(() -> expense.validateModifiable(userId, futureDate))
			.isInstanceOf(NotUpdateExpenseException.class);
	}
} 