package com.prography.budgetbuddiesbackend.category.adapter.in;

import java.util.List;

import com.prography.budgetbuddiesbackend.category.domain.Category;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AllCategoryResponse {
	private final List<CategoryResponse> categoryResponses;

	public static AllCategoryResponse of(List<Category> categories) {
		List<CategoryResponse> categoryResponses = categories.stream()
			.map(category -> new CategoryResponse(category.getCategoryId(), category.getName(), category.isDefault()))
			.toList();

		return new AllCategoryResponse(categoryResponses);
	}

	@Getter
	@AllArgsConstructor
	public static class CategoryResponse {
		private Long categoryId;
		private String name;
		private boolean isDefault;
	}
}
