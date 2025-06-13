package com.prography.budgetbuddiesbackend.report.domain.expense.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prography.budgetbuddiesbackend.report.domain.category.entity.Category;
import com.prography.budgetbuddiesbackend.report.domain.category.service.CategoryService;
import com.prography.budgetbuddiesbackend.report.domain.expense.controller.dto.request.RegisterExpenseRequest;
import com.prography.budgetbuddiesbackend.report.domain.expense.controller.dto.request.UpdateExpenseRequest;
import com.prography.budgetbuddiesbackend.report.domain.expense.entity.Expense;
import com.prography.budgetbuddiesbackend.report.domain.user.entity.User;
import com.prography.budgetbuddiesbackend.report.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ExpenseFacadeService implements ExpenseUseCase {
	private final ExpenseService expenseService;
	private final ExpenseMapper mapper;

	private final CategoryService categoryService;
	private final UserService userService;

	public void registerExpense(RegisterExpenseRequest request, Long userId) {
		User user = userService.findById(userId);
		Category category = categoryService.findById(request.categoryId());
		Expense expense = mapper.registerEntityResponseToEntity(request, category, user);

		expenseService.save(expense);
	}

	public void updateExpense(UpdateExpenseRequest request, Long userId) {
		Expense expense = expenseService.findById(request.expenseId());
		expense.validateModifiable(userId, request.expenseAt());

		Category category = categoryService.findById(request.categoryId());
		expense.update(category, request.expenseAt());

		expenseService.save(expense);
	}

	public void deleteExpense(Long expenseId, Long userId) {
		Expense expense = expenseService.findById(expenseId);
		expense.validateOwner(userId);

		expenseService.delete(expense);
	}
}
