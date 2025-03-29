package com.prography.budgetbuddiesbackend.report.adapter.out.persistence;

import org.springframework.stereotype.Component;

import com.prography.budgetbuddiesbackend.report.application.port.out.category.CreateCategoryCommand;
import com.prography.budgetbuddiesbackend.report.domain.Category;

@Component
class CategoryMapper {
	CategoryEntity createCategoryCommandToEntity(CreateCategoryCommand command) {
		return new CategoryEntity(null, command.name(), false, command.userId());
	}

	Category entityToCategory(CategoryEntity categoryEntity) {
		return new Category(categoryEntity.getId(), categoryEntity.getName(), categoryEntity.getIsDefault());
	}
}
