package com.prography.budgetbuddiesbackend.report.domain.expense.service;

import org.springframework.stereotype.Component;

import com.prography.budgetbuddiesbackend.report.domain.category.entity.Category;
import com.prography.budgetbuddiesbackend.report.domain.expense.controller.dto.request.RegisterExpenseRequest;
import com.prography.budgetbuddiesbackend.report.domain.expense.entity.Expense;
import com.prography.budgetbuddiesbackend.report.domain.user.entity.User;

@Component
public class ExpenseMapper {
	public Expense registerEntityResponseToEntity(RegisterExpenseRequest request, Category category, User user) {
		return Expense.of(user, category, request.amount(), request.description(), request.expenseAt());
	}
}
