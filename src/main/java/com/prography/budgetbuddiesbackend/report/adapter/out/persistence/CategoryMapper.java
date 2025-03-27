package com.prography.budgetbuddiesbackend.report.adapter.out.persistence;

import org.springframework.stereotype.Component;

import com.prography.budgetbuddiesbackend.report.application.port.out.category.CreateCategoryCommand;
import com.prography.budgetbuddiesbackend.report.domain.Category;
import com.prography.budgetbuddiesbackend.report.domain.ConsumptionGoal;

@Component
class CategoryMapper {
	public Category categoryEntityToCategory(CategoryEntity categoryEntity) {
		return new Category(categoryEntity.getId(), categoryEntity.getName(), categoryEntity.getIsDefault());
	}

	public CategoryEntity consumptionGoalToCategoryEntity(UserEntity user, ConsumptionGoal consumptionGoal) {
		return new CategoryEntity(null, consumptionGoal.getCategoryName(), consumptionGoal.getCap().value(), false,
			user);
	}

	public CategoryEntity createCategoryCommandToCategoryEntity(CreateCategoryCommand command, UserEntity userEntity) {
		return new CategoryEntity(null, command.name(), 0, false, userEntity);
	}
}
