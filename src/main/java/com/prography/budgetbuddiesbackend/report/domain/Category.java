package com.prography.budgetbuddiesbackend.report.domain;

import com.prography.budgetbuddiesbackend.report.domain.enums.CategoryType;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Category {
	private Long id;
	private Long userId;
	private CategoryType type;
	private String name;

	public static Category of(Long id, Long userId ,CategoryType type, String name) {
		return new Category(id, userId, type, name);
	}
}
