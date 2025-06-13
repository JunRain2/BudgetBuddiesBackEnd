package com.prography.budgetbuddiesbackend.report.domain.category.service;

import com.prography.budgetbuddiesbackend.report.domain.category.entity.Category;

public interface CategoryExpenseService {
	void reassignCategory(Category deletedCategory, Category uncategorized);
}
