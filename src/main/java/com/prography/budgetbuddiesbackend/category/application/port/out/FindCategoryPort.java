package com.prography.budgetbuddiesbackend.category.application.port.out;

import java.util.List;

import com.prography.budgetbuddiesbackend.category.domain.Category;

public interface FindCategoryPort {
	Category findCategory(Long categoryId);

	// 유저 카테고리 -> 디폴트 카테고리를 포함
	List<Category> findUserAndDefaultCategory(Long userId);
}
