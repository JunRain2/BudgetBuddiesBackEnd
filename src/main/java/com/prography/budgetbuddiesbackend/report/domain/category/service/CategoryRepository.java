package com.prography.budgetbuddiesbackend.report.domain.category.service;

import java.util.List;
import java.util.Set;

import com.prography.budgetbuddiesbackend.report.domain.category.dto.request.RegisterCategoryRequest;
import com.prography.budgetbuddiesbackend.report.domain.category.entity.Category;

public interface CategoryRepository {
	Category registerCategory(RegisterCategoryRequest request, Long userId);

	Set<String> findUserCategoryNames(Long userId);

	List<Category> findUserCategories(Long userId);

	void deleteCategory(Category category);

	Category findById(Long categoryId);
}
