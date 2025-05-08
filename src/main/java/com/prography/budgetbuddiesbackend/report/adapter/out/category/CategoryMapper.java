package com.prography.budgetbuddiesbackend.report.adapter.out.category;

import org.springframework.stereotype.Component;

import com.prography.budgetbuddiesbackend.report.adapter.out.UserEntity;
import com.prography.budgetbuddiesbackend.report.domain.Category;
import com.prography.budgetbuddiesbackend.report.domain.enums.CategoryType;

@Component
class CategoryMapper {
	CategoryEntity entityFromDomain(Category category, UserEntity user) {
		Boolean isDefault = category.getType() != CategoryType.CUSTOM;
		return CategoryEntity.of(user, isDefault, category.getName());
	}

	public Category domainFromEntity(CategoryEntity category) {
		CategoryType type = category.getIsDefault() ? CategoryType.DEFAULT : CategoryType.CUSTOM;
		return Category.of(category.getId(), category.getUser().getId(), type, category.getName());
	}
}
