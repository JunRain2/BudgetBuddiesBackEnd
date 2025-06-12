package com.prography.budgetbuddiesbackend.report.domain.category.service;

import java.time.YearMonth;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prography.budgetbuddiesbackend.report.domain.category.controller.dto.request.RegisterCategoryRequest;
import com.prography.budgetbuddiesbackend.report.domain.category.controller.dto.response.UserCategoryResponse;
import com.prography.budgetbuddiesbackend.report.domain.category.entity.Category;
import com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.entity.ConsumptionGoal;
import com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.service.ConsumptionGoalDomainService;
import com.prography.budgetbuddiesbackend.report.domain.expense.service.ExpenseDomainService;
import com.prography.budgetbuddiesbackend.report.domain.user.entity.User;
import com.prography.budgetbuddiesbackend.report.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class CategoryFacadeService implements CategoryUseCase{
	private final CategoryServiceImpl categoryDomainService;
	private final CategoryMapper mapper;

	private final ExpenseDomainService expenseDomainService;
	private final ConsumptionGoalDomainService consumptionGoalDomainService;
	private final UserService userService;

	public void registerCategory(RegisterCategoryRequest request, Long userId) {
		User user = userService.findById(userId);
		Category newCategory = mapper.registerCategoryRequestToEntity(request, user);
		newCategory = categoryDomainService.save(newCategory);

		YearMonth now = YearMonth.now();
		ConsumptionGoal newConsumptionGoal = newCategory.createInitialGoal(now);
		consumptionGoalDomainService.save(newConsumptionGoal);
	}

	public void deleteCategory(Long categoryId, Long userId) {
		Category deletedCategory = categoryDomainService.findById(categoryId);
		deletedCategory.validateModifiable(userId);

		consumptionGoalDomainService.deleteAllByCategory(deletedCategory);

		Category uncategorizedCategory = categoryDomainService.findUncategorizedCategory();
		expenseDomainService.reassignCategory(deletedCategory, uncategorizedCategory);

		categoryDomainService.delete(deletedCategory);
	}

	@Transactional(readOnly = true)
	public List<UserCategoryResponse> findUserCategories(Long userId) {
		List<Category> userCategories = categoryDomainService.findUserCategories(userId);
		return userCategories.stream().map(mapper::entityToUserCategoryResponse).toList();
	}
}
