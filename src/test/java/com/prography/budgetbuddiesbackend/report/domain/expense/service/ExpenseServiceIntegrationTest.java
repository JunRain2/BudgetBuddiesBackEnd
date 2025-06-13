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
import com.prography.budgetbuddiesbackend.report.domain.user.entity.User;
import com.prography.budgetbuddiesbackend.report.domain.user.repository.UserRepository;

@ServiceIntegrationTest
class ExpenseServiceIntegrationTest {
	@Autowired
	ExpenseUseCase expenseService;
	@Autowired
	ExpenseRepository expenseRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	CategoryRepository categoryRepository;

	@Test
	void 금액이_0원일_때_등록_실패() {
		// given
		User user = userRepository.save(User.of());
		Category category = categoryRepository.findAll().get(0);
		RegisterExpenseRequest req = new RegisterExpenseRequest(category.getId(), 0, "테스트",
			LocalDate.now().minusDays(1));
		// when & then
		assertThatThrownBy(() -> expenseService.registerExpense(req, user.getId()))
			.isInstanceOf(NotRegisterExpenseException.class);
	}

	@Test
	void 금액이_1원일_때_등록_성공() {
		// given
		User user = userRepository.save(User.of());
		Category category = categoryRepository.findAll().get(0);
		RegisterExpenseRequest req = new RegisterExpenseRequest(category.getId(), 1, "테스트",
			LocalDate.now().minusDays(1));
		// when
		expenseService.registerExpense(req, user.getId());
		// then
		Expense expense = expenseRepository.findAll()
			.stream()
			.filter(e -> e.getUser().getId().equals(user.getId()))
			.findFirst()
			.orElseThrow();
		assertThat(expense.getAmount()).isEqualTo(1);
	}

	@Test
	void 과거_날짜_등록_성공() {
		// given
		User user = userRepository.save(User.of());
		Category category = categoryRepository.findAll().get(0);
		RegisterExpenseRequest req = new RegisterExpenseRequest(category.getId(), 1000, "테스트",
			LocalDate.now().minusDays(1));
		// when
		expenseService.registerExpense(req, user.getId());
		// then
		Expense expense = expenseRepository.findAll()
			.stream()
			.filter(e -> e.getUser().getId().equals(user.getId()))
			.findFirst()
			.orElseThrow();
		assertThat(expense.getExpenseAt()).isEqualTo(LocalDate.now().minusDays(1));
	}

	@Test
	void 오늘_날짜_등록_성공() {
		// given
		User user = userRepository.save(User.of());
		Category category = categoryRepository.findAll().get(0);
		RegisterExpenseRequest req = new RegisterExpenseRequest(category.getId(), 1000, "테스트", LocalDate.now());
		// when
		expenseService.registerExpense(req, user.getId());
		// then
		Expense expense = expenseRepository.findAll()
			.stream()
			.filter(e -> e.getUser().getId().equals(user.getId()))
			.findFirst()
			.orElseThrow();
		assertThat(expense.getExpenseAt()).isEqualTo(LocalDate.now());
	}

	@Test
	void 미래_날짜_등록_실패() {
		// given
		User user = userRepository.save(User.of());
		Category category = categoryRepository.findAll().get(0);
		RegisterExpenseRequest req = new RegisterExpenseRequest(category.getId(), 1000, "테스트",
			LocalDate.now().plusDays(1));
		// when & then
		assertThatThrownBy(() -> expenseService.registerExpense(req, user.getId()))
			.isInstanceOf(NotRegisterExpenseException.class);
	}
} 