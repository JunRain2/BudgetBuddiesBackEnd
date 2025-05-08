package com.prography.budgetbuddiesbackend.report.application;

import static com.prography.budgetbuddiesbackend.report.domain.enums.CategoryType.*;

import org.springframework.stereotype.Component;

import com.prography.budgetbuddiesbackend.report.application.port.in.RegisterCategoryCommand;
import com.prography.budgetbuddiesbackend.report.domain.Category;

@Component
class CategoryMapper {
	Category categoryFromRegisterCategoryCommand(RegisterCategoryCommand command) {
		return Category.of(null, command.userId(), CUSTOM, command.name());
	}
}
