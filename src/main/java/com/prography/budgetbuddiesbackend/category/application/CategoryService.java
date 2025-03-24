package com.prography.budgetbuddiesbackend.category.application;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.prography.budgetbuddiesbackend.category.adapter.in.AllCategoryResponse;
import com.prography.budgetbuddiesbackend.category.application.port.in.CategoryUseCase;
import com.prography.budgetbuddiesbackend.category.application.port.out.DeleteCategoryPort;
import com.prography.budgetbuddiesbackend.category.application.port.out.FindCategoryPort;
import com.prography.budgetbuddiesbackend.category.application.port.out.RegisterCategoryCommand;
import com.prography.budgetbuddiesbackend.category.application.port.out.RegisterCategoryPort;
import com.prography.budgetbuddiesbackend.category.domain.Category;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
public class CategoryService implements CategoryUseCase {
	private final FindCategoryPort findCategoryPort;
	private final RegisterCategoryPort registerCategoryPort;
	private final DeleteCategoryPort deleteCategoryPort;

	@Override
	public void registerCategory(Long userId, String categoryName) {
		RegisterCategoryCommand registerCategoryCommand = RegisterCategoryCommand.of(userId, categoryName, false);
		registerCategoryPort.registerCategory(registerCategoryCommand);
	}

	@Override
	public void deleteCategory(Long userId, Long categoryId) {
		Category category = findCategoryPort.findCategory(categoryId);

		if (!category.canModify(userId)) {
			throw new NotModifyCategoryException();
		}
		deleteCategoryPort.deleteCategory(category.getCategoryId());
		// TODO 이번달 삭제된 카테고리의 소비목표는 소비없음으로 변경
	}

	@Override
	public AllCategoryResponse getAllCategory(Long userId) {
		List<Category> categories = findCategoryPort.findUserAndDefaultCategory(userId);
		return AllCategoryResponse.of(categories);
	}
}
