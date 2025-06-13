package com.prography.budgetbuddiesbackend.report.domain.expense.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.prography.budgetbuddiesbackend.report.domain.category.entity.Category;
import com.prography.budgetbuddiesbackend.report.domain.expense.entity.Expense;
import com.prography.budgetbuddiesbackend.report.domain.expense.exception.NotFoundExpenseException;
import com.prography.budgetbuddiesbackend.report.domain.expense.repository.ExpenseRepository;
import com.prography.budgetbuddiesbackend.report.domain.user.entity.User;

@ExtendWith(MockitoExtension.class)
class ExpenseDomainServiceTest {
	@Mock
	private ExpenseService expenseService;

	@Mock
	private ExpenseRepository expenseRepository;

	private User user;
	private Category category;
	private Expense expense;

	@BeforeEach
	void setUp() {
		user = User.of();
		category = Category.of(user, "식비");
		expense = Expense.of(user, category, 10000, "점심", LocalDate.now());
	}

	@Test
	@DisplayName("지출을 저장한다")
	void save_정상동작() {
		// given
		when(expenseService.save(any(Expense.class))).thenReturn(expense);

		// when
		Expense savedExpense = expenseService.save(expense);

		// then
		assertThat(savedExpense).isEqualTo(expense);
		verify(expenseService).save(expense);
	}

	@Test
	@DisplayName("지출을 삭제한다")
	void delete_정상동작() {
		// given
		doNothing().when(expenseService).delete(any(Expense.class));

		// when
		expenseService.delete(expense);

		// then
		verify(expenseService).delete(expense);
	}

	@Test
	@DisplayName("ID로 지출을 조회한다")
	void findById_정상동작() {
		// given
		when(expenseService.findById(1L)).thenReturn(expense);

		// when
		Expense foundExpense = expenseService.findById(1L);

		// then
		assertThat(foundExpense).isEqualTo(expense);
		verify(expenseService).findById(1L);
	}

	@Test
	@DisplayName("존재하지 않는 ID로 조회시 예외가 발생한다")
	void findById_없으면_예외() {
		// given
		when(expenseService.findById(1L)).thenThrow(NotFoundExpenseException.class);

		// when & then
		assertThatThrownBy(() -> expenseService.findById(1L))
			.isInstanceOf(NotFoundExpenseException.class);
		verify(expenseService).findById(1L);
	}
} 