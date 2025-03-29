package com.prography.budgetbuddiesbackend.report.adapter.in;

import java.util.List;

import com.prography.budgetbuddiesbackend.report.application.port.in.category.FindUserCategoryResult;

import lombok.Getter;

@Getter
public class UserCategoryListResponse {
	private final List<UserCategoryResponse> categoryList;

	public UserCategoryListResponse(List<FindUserCategoryResult> results) {
		this.categoryList = results.stream().map(
			r -> new UserCategoryResponse(r.categoryId(), r.name(), r.isDefault())
		).toList();
	}

	private record UserCategoryResponse(
		Long categoryId,
		String name,
		boolean isDefault
	) {
	}

}
