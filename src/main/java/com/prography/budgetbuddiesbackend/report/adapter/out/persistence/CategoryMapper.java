package com.prography.budgetbuddiesbackend.report.adapter.out.persistence;

import org.springframework.stereotype.Component;

import com.prography.budgetbuddiesbackend.report.application.port.out.category.CreateCategoryCommand;
import com.prography.budgetbuddiesbackend.report.domain.Category;

@Component
class CategoryMapper {
	public Category categoryEntityToCategory(CategoryEntity categoryEntity) {
		return new Category(categoryEntity.getId(), categoryEntity.getName(), categoryEntity.getIsDefault());
	}

	public CategoryEntity createCategoryCommandToCategoryEntity(CreateCategoryCommand command) {
		return new CategoryEntity(null, command.name(), 0, false, command.userId());
	}
}
