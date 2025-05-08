package com.prography.budgetbuddiesbackend.report.application;

import static com.prography.budgetbuddiesbackend.report.domain.enums.CategoryType.*;

import org.springframework.stereotype.Component;

import com.prography.budgetbuddiesbackend.report.application.port.in.RegisterCategoryCommand;
import com.prography.budgetbuddiesbackend.report.application.port.in.UserCategoryResponse;
import com.prography.budgetbuddiesbackend.report.domain.Category;

@Component
class CategoryMapper {
	Category registerCategoryCommandToDomain(RegisterCategoryCommand command) {
		return Category.of(null, command.userId(), CUSTOM, command.name());
	}

	public UserCategoryResponse domainToUserCategoryResponse(Category category) {
		return new UserCategoryResponse(category.getId(), category.getName());
	}
}
