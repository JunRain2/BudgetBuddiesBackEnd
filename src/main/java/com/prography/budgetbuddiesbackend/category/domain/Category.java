package com.prography.budgetbuddiesbackend.category.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Category {
	private Long categoryId;
	private Long userId;
	private String name;
	private boolean isDefault;

	public static Category of(Long categoryId, Long userId, String name, boolean isDefault) {
		return new Category(categoryId, userId, name, isDefault);
	}

	public boolean canModify(Long userId) {
		return !this.isDefault() && this.getUserId().equals(userId);
	}
}
