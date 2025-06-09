package com.prography.budgetbuddiesbackend.report.domain.category.service;

import static com.prography.budgetbuddiesbackend.report.domain.category.entity.CategoryType.*;

import java.time.YearMonth;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prography.budgetbuddiesbackend.report.domain.category.dto.request.RegisterCategoryRequest;
import com.prography.budgetbuddiesbackend.report.domain.category.dto.response.UserCategoryResponse;
import com.prography.budgetbuddiesbackend.report.domain.category.entity.Category;
import com.prography.budgetbuddiesbackend.report.domain.category.exception.DuplicateCategoryNameException;
import com.prography.budgetbuddiesbackend.report.domain.category.exception.UnmodifiableCategoryException;
import com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.entity.ConsumptionGoal;
import com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.service.ConsumptionGoalDomainService;
import com.prography.budgetbuddiesbackend.report.domain.expense.service.ExpenseDomainService;
import com.prography.budgetbuddiesbackend.report.domain.user.entity.User;
import com.prography.budgetbuddiesbackend.report.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class CategoryService {
	private final CategoryDomainService categoryDomainService;
	private final CategoryMapper mapper;

	private final ExpenseDomainService expenseDomainService;
	private final ConsumptionGoalDomainService consumptionGoalDomainService;
	private final UserService userService;

	public void registerCategory(RegisterCategoryRequest request, Long userId) {
		validateCategoryCreatable(request.name(), userId);

		User user = userService.findById(userId);
		Category newCategory = mapper.registerCategoryRequestToEntity(request, user);
		newCategory = categoryDomainService.save(newCategory);

		ConsumptionGoal newConsumptionGoal = newCategory.createInitialGoal(YearMonth.now());
		consumptionGoalDomainService.save(newConsumptionGoal);
	}

	void validateCategoryCreatable(String categoryName, Long userId) {
		Set<String> userCategoryNames = categoryDomainService.findCategoryNamesByUser(userId);
		if (userCategoryNames.contains(categoryName)) {
			throw new DuplicateCategoryNameException();
		}
	}

	public void deleteCategory(Long categoryId, Long userId) {
		Category deletedCategory = categoryDomainService.findById(categoryId);
		validateCategoryModifiable(userId, deletedCategory);

		consumptionGoalDomainService.deleteAllByCategory(deletedCategory);
		Category uncategorized = categoryDomainService.findById(1L);
		expenseDomainService.reassignCategory(deletedCategory, uncategorized);
		categoryDomainService.delete(deletedCategory);
	}

	private void validateCategoryModifiable(Long userId, Category category) {
		if (DEFAULT.equals(category.getType()) || !category.getUser().getId().equals(userId)) {
			throw new UnmodifiableCategoryException();
		}
	}

	@Transactional(readOnly = true)
	public List<UserCategoryResponse> findUserCategories(Long userId) {
		List<Category> userCategories = categoryDomainService.findCategoriesByUser(userId);
		return userCategories.stream().map(mapper::entityToUserCategoryResponse).toList();
	}

	public Category findById(Long categoryId) {
		return categoryDomainService.findById(categoryId);
	}
}
