package com.prography.budgetbuddiesbackend.report.domain.expense.service;

import com.prography.budgetbuddiesbackend.report.domain.expense.entity.Expense;

public interface ExpenseService {
	Expense save(Expense expense);

	void delete(Expense expense);

	Expense findById(Long id);
}

