package com.prography.budgetbuddiesbackend.report.domain.category.service;

import java.util.List;

import com.prography.budgetbuddiesbackend.report.domain.category.entity.Category;

public interface CategoryService {
	Category save(Category category);

	void delete(Category category);

	Category findById(Long id);

	List<Category> findUserCategories(Long userId);

	Category findUncategorizedCategory();
}
