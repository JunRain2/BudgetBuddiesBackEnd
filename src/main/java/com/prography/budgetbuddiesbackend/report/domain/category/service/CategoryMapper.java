package com.prography.budgetbuddiesbackend.report.domain.category.service;

import org.springframework.stereotype.Component;

import com.prography.budgetbuddiesbackend.report.domain.category.controller.dto.request.RegisterCategoryRequest;
import com.prography.budgetbuddiesbackend.report.domain.category.controller.dto.response.UserCategoryResponse;
import com.prography.budgetbuddiesbackend.report.domain.category.entity.Category;
import com.prography.budgetbuddiesbackend.user.entity.UserId;

@Component
public class CategoryMapper {
	public UserCategoryResponse entityToUserCategoryResponse(Category entity) {
		return new UserCategoryResponse(entity.getId().getId(), entity.getName());
	}

	public Category registerCategoryRequestToEntity(RegisterCategoryRequest request, UserId userId) {
		return Category.of(userId, request.name());
	}
}
