package com.prography.budgetbuddiesbackend.category.application.port.out;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RegisterCategoryCommand {
	private final Long userId;
	private final String name;
	private final boolean isDefault;

	public static RegisterCategoryCommand of(Long userId, String name, boolean isDefault) {
		return new RegisterCategoryCommand(userId, name, isDefault);
	}
}
