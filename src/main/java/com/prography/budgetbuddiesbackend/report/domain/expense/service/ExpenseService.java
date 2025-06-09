package com.prography.budgetbuddiesbackend.report.domain.expense.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prography.budgetbuddiesbackend.report.domain.category.entity.Category;
import com.prography.budgetbuddiesbackend.report.domain.category.service.CategoryDomainService;
import com.prography.budgetbuddiesbackend.report.domain.expense.dto.request.RegisterExpenseRequest;
import com.prography.budgetbuddiesbackend.report.domain.expense.dto.request.UpdateExpenseRequest;
import com.prography.budgetbuddiesbackend.report.domain.expense.entity.Expense;
import com.prography.budgetbuddiesbackend.report.domain.expense.exception.NotRegisterExpenseException;
import com.prography.budgetbuddiesbackend.report.domain.expense.exception.NotUpdateExpenseException;
import com.prography.budgetbuddiesbackend.report.domain.user.entity.User;
import com.prography.budgetbuddiesbackend.report.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ExpenseService {
	private final ExpenseMapper mapper;

	private final CategoryDomainService categoryDomainService;
	private final UserService userService;
	private final ExpenseDomainService expenseDomainService;

	public void reassignCategory(Category deletedCategory, Category uncategorized) {
		expenseDomainService.reassignCategory(deletedCategory, uncategorized);
	}

	public void registerExpense(RegisterExpenseRequest request, Long userId) {
		LocalDate now = LocalDate.now();
		if (now.isBefore(request.expenseAt()) || request.amount() <= 0) {
			throw new NotRegisterExpenseException();
		}

		User user = userService.findById(userId);
		Category category = categoryDomainService.findById(request.categoryId());
		Expense expense = mapper.registerEntityResponseToEntity(request, category, user);

		expenseDomainService.save(expense);
	}

	public void updateExpense(UpdateExpenseRequest request, Long userId) {
		Expense expense = expenseDomainService.findById(request.expenseId());
		validateUpdateExpense(expense, request, userId);

		Category category = categoryDomainService.findById(request.categoryId());
		expense.update(category, request.expenseAt());

		expenseDomainService.save(expense);
	}

	public void deleteById(Long expenseId, Long userId) {
		Expense expense = expenseDomainService.findById(expenseId);
		validateDeleteExpense(expense, userId);

		expenseDomainService.delete(expense);
	}

	private void validateUpdateExpense(Expense expense, UpdateExpenseRequest request, Long userId) {
		LocalDate now = LocalDate.now();

		if (!expense.getUser().getId().equals(userId) || expense.getCategory().getId().equals(request.categoryId())
			|| now.isBefore(request.expenseAt())) {

			throw new NotUpdateExpenseException();
		}
	}

	private void validateDeleteExpense(Expense expense, Long userId) {
		if (!expense.getUser().getId().equals(userId)) {
			throw new NotUpdateExpenseException();
		}
	}
}
