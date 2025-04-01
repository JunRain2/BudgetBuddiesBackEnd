package com.prography.budgetbuddiesbackend.report.application.port.out.category;

import java.util.List;

import com.prography.budgetbuddiesbackend.report.domain.Category;

public interface FindCategoryPort {
	List<Category> findUserAndDefaultCategory(Long userId);

	Category findUserCategory(Long userId, Long categoryId);

	Category findCategory(Long categoryId);
}
