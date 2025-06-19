package com.prography.budgetbuddiesbackend.report.domain.category.service;

import org.springframework.stereotype.Component;

import com.prography.budgetbuddiesbackend.report.domain.category.controller.dto.request.RegisterCategoryRequest;
import com.prography.budgetbuddiesbackend.report.domain.category.controller.dto.response.UserCategoryResponse;
import com.prography.budgetbuddiesbackend.report.domain.category.entity.Category;

@Component
public class CategoryMapper {
	public UserCategoryResponse entityToUserCategoryResponse(Category entity) {
		return new UserCategoryResponse(entity.getId(), entity.getName());
	}

	public Category registerCategoryRequestToEntity(RegisterCategoryRequest request, Long userId) {
		return Category.of(userId, request.name());
	}
}
