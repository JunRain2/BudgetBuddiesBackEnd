package com.prography.budgetbuddiesbackend.report.domain;

import com.prography.budgetbuddiesbackend.report.domain.enums.CategoryType;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Category {
	private Long id;
	private CategoryType type;
	private String name;

	public static Category of(Long id, CategoryType type, String name) {
		return new Category(id, type, name);
	}
}
