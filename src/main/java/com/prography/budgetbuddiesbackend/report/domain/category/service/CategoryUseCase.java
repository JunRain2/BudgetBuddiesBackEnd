package com.prography.budgetbuddiesbackend.report.domain.category.service;

import java.util.List;

import com.prography.budgetbuddiesbackend.report.domain.category.controller.dto.request.RegisterCategoryRequest;
import com.prography.budgetbuddiesbackend.report.domain.category.controller.dto.response.UserCategoryResponse;
import com.prography.budgetbuddiesbackend.report.domain.category.entity.CategoryId;
import com.prography.budgetbuddiesbackend.user.entity.UserId;

public interface CategoryUseCase {
	void registerCategory(RegisterCategoryRequest request, UserId userId);

	void deleteCategory(CategoryId categoryId, UserId userId);

	List<UserCategoryResponse> getUserCategories(UserId userId);
}

