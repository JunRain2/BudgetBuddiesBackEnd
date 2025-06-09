package com.prography.budgetbuddiesbackend.report.domain.expense.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prography.budgetbuddiesbackend.report.domain.category.entity.Category;
import com.prography.budgetbuddiesbackend.report.domain.expense.entity.Expense;
import com.prography.budgetbuddiesbackend.report.domain.expense.exception.NotFoundExpenseException;
import com.prography.budgetbuddiesbackend.report.domain.expense.repository.ExpenseRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ExpenseDomainService {
	private final ExpenseRepository expenseRepository;

	public Expense save(Expense expense) {
		return expenseRepository.save(expense);
	}

	public void reassignCategory(Category deletedCategory, Category uncategorized) {
		expenseRepository.clearCategoryReference(deletedCategory, uncategorized);
	}

	public void delete(Expense expense) {
		expenseRepository.delete(expense);
	}

	public Expense findById(Long id) {
		return expenseRepository.findById(id).orElseThrow(NotFoundExpenseException::new);
	}
} 