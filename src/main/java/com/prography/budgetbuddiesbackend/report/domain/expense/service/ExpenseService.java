package com.prography.budgetbuddiesbackend.report.domain.expense.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prography.budgetbuddiesbackend.report.domain.category.entity.Category;
import com.prography.budgetbuddiesbackend.report.domain.expense.repository.ExpenseRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ExpenseService {
	private final ExpenseRepository expenseRepository;

	public void clearCategoryReference(Category category) {
		expenseRepository.clearCategoryReference(category);
	}
}
