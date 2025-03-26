package com.prography.budgetbuddiesbackend.report.adapter.in;

import java.util.List;

import com.prography.budgetbuddiesbackend.report.domain.Category;

import lombok.Getter;

@Getter
public class UserCategoryListResponse {
	private final List<UserCategoryResponse> categoryList;

	public UserCategoryListResponse(List<Category> categoryList) {
		this.categoryList = categoryList.stream()
			.map(category -> new UserCategoryResponse(category.getCategoryId(), category.getName(),
				category.isDefault()))
			.toList();
	}

	private record UserCategoryResponse(Long categoryId, String name, boolean isDefault) {
	}
}