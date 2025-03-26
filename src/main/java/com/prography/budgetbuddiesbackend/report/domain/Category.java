package com.prography.budgetbuddiesbackend.report.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Category {
	private Long categoryId;
	private String name;
	private boolean isDefault;
}
