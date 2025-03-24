package com.prography.budgetbuddiesbackend.category.application.port.in;

import com.prography.budgetbuddiesbackend.category.adapter.in.AllCategoryResponse;

public interface CategoryUseCase {
	void registerCategory(Long userId, String categoryName);

	void deleteCategory(Long userId, Long categoryId);

	AllCategoryResponse getAllCategory(Long userId);
}
