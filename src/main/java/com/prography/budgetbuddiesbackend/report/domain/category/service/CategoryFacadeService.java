package com.prography.budgetbuddiesbackend.report.domain.category.service;

import java.time.YearMonth;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prography.budgetbuddiesbackend.report.domain.category.controller.dto.request.RegisterCategoryRequest;
import com.prography.budgetbuddiesbackend.report.domain.category.controller.dto.response.UserCategoryResponse;
import com.prography.budgetbuddiesbackend.report.domain.category.entity.Category;
import com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.entity.ConsumptionGoal;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class CategoryFacadeService implements CategoryUseCase {
	private final CategoryService categoryService;
	private final CategoryMapper mapper;

	private final CategoryExpenseService expenseService;
	private final CategoryConsumptionGoalService consumptionGoalService;

	public void registerCategory(RegisterCategoryRequest request, Long userId) {
		Category newCategory = mapper.registerCategoryRequestToEntity(request, userId);
		newCategory = categoryService.save(newCategory);

		YearMonth now = YearMonth.now();
		ConsumptionGoal newConsumptionGoal = newCategory.createInitialGoal(now);
		consumptionGoalService.save(newConsumptionGoal);
	}

	public void deleteCategory(Long categoryId, Long userId) {
		Category deletedCategory = categoryService.findById(categoryId);
		deletedCategory.validateModifiable(userId);

		consumptionGoalService.deleteAllByCategory(deletedCategory);

		Category uncategorizedCategory = categoryService.findUncategorizedCategory();
		expenseService.reassignCategory(deletedCategory, uncategorizedCategory);

		categoryService.delete(deletedCategory);
	}

	@Transactional(readOnly = true)
	public List<UserCategoryResponse> getUserCategories(Long userId) {
		List<Category> userCategories = categoryService.findUserCategories(userId);
		return userCategories.stream().map(mapper::entityToUserCategoryResponse).toList();
	}
}
