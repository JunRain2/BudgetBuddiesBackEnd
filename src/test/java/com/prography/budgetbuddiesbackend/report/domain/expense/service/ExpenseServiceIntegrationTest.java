package com.prography.budgetbuddiesbackend.report.domain.expense.service;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.prography.budgetbuddiesbackend.common.ServiceIntegrationTest;
import com.prography.budgetbuddiesbackend.report.domain.category.entity.Category;
import com.prography.budgetbuddiesbackend.report.domain.category.repository.CategoryRepository;
import com.prography.budgetbuddiesbackend.report.domain.expense.controller.dto.request.RegisterExpenseRequest;
import com.prography.budgetbuddiesbackend.report.domain.expense.entity.Expense;
import com.prography.budgetbuddiesbackend.report.domain.expense.exception.NotRegisterExpenseException;
import com.prography.budgetbuddiesbackend.report.domain.expense.repository.ExpenseRepository;
import com.prography.budgetbuddiesbackend.user.entity.UserId;
import com.prography.budgetbuddiesbackend.report.domain.expense.service.command.RegisterExpenseCommand;
import com.prography.budgetbuddiesbackend.report.domain.category.entity.CategoryId;

@ServiceIntegrationTest
class ExpenseServiceIntegrationTest {
	@Autowired
	ExpenseUseCase expenseService;
	@Autowired
	ExpenseRepository expenseRepository;
	@Autowired
	CategoryRepository categoryRepository;

	@Test
	void 금액이_0원일_때_등록_실패() {
		// given
		UserId userId = UserId.generate();
		Category category = categoryRepository.findAll().get(0);
		RegisterExpenseCommand cmd = new RegisterExpenseCommand(category.getId(), 0, "테스트",
			LocalDate.now().minusDays(1));
		// when & then
		assertThatThrownBy(() -> expenseService.registerExpense(cmd, userId))
			.isInstanceOf(NotRegisterExpenseException.class);
	}

	@Test
	void 금액이_1원일_때_등록_성공() {
		// given
		UserId userId = UserId.generate();
		Category category = categoryRepository.findAll().get(0);
		RegisterExpenseCommand cmd = new RegisterExpenseCommand(category.getId(), 1, "테스트",
			LocalDate.now().minusDays(1));
		// when
		expenseService.registerExpense(cmd, userId);
		// then
		Expense expense = expenseRepository.findAll()
			.stream()
			.filter(e -> e.getUserId().equals(userId))
			.findFirst()
			.orElseThrow();
		assertThat(expense.getAmount()).isEqualTo(1);
	}

	@Test
	void 과거_날짜_등록_성공() {
		// given
		UserId userId = UserId.generate();
		Category category = categoryRepository.findAll().get(0);
		RegisterExpenseCommand cmd = new RegisterExpenseCommand(category.getId(), 1000, "테스트",
			LocalDate.now().minusDays(1));
		// when
		expenseService.registerExpense(cmd, userId);
		// then
		Expense expense = expenseRepository.findAll()
			.stream()
			.filter(e -> e.getUserId().equals(userId))
			.findFirst()
			.orElseThrow();
		assertThat(expense.getExpenseAt()).isEqualTo(LocalDate.now().minusDays(1));
	}

	@Test
	void 오늘_날짜_등록_성공() {
		// given
		UserId userId = UserId.generate();
		Category category = categoryRepository.findAll().get(0);
		RegisterExpenseCommand cmd = new RegisterExpenseCommand(category.getId(), 1000, "테스트", LocalDate.now());
		// when
		expenseService.registerExpense(cmd, userId);
		// then
		Expense expense = expenseRepository.findAll()
			.stream()
			.filter(e -> e.getUserId().equals(userId))
			.findFirst()
			.orElseThrow();
		assertThat(expense.getExpenseAt()).isEqualTo(LocalDate.now());
	}

	@Test
	void 미래_날짜_등록_실패() {
		// given
		UserId userId = UserId.generate();
		Category category = categoryRepository.findAll().get(0);
		RegisterExpenseCommand cmd = new RegisterExpenseCommand(category.getId(), 1000, "테스트",
			LocalDate.now().plusDays(1));
		// when & then
		assertThatThrownBy(() -> expenseService.registerExpense(cmd, userId))
			.isInstanceOf(NotRegisterExpenseException.class);
	}

	@Test
	void 금액이_음수일_때_등록_실패() {
		// given
		UserId userId = UserId.generate();
		Category category = categoryRepository.findAll().get(0);
		RegisterExpenseCommand cmd = new RegisterExpenseCommand(category.getId(), -1000, "테스트",
			LocalDate.now().minusDays(1));
		// when & then
		assertThatThrownBy(() -> expenseService.registerExpense(cmd, userId))
			.isInstanceOf(NotRegisterExpenseException.class);
	}
} 