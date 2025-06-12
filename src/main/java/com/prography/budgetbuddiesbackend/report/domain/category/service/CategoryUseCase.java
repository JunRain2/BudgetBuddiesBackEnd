package com.prography.budgetbuddiesbackend.report.domain.category.service;

import java.util.List;

import com.prography.budgetbuddiesbackend.report.domain.category.controller.dto.request.RegisterCategoryRequest;
import com.prography.budgetbuddiesbackend.report.domain.category.controller.dto.response.UserCategoryResponse;

public interface CategoryUseCase {
	void registerCategory(RegisterCategoryRequest request, Long userId);

	void deleteCategory(Long categoryId, Long userId);

	List<UserCategoryResponse> findUserCategories(Long userId);
}

