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
import com.prography.budgetbuddiesbackend.report.domain.category.exception.NotFoundCategoryException;
import com.prography.budgetbuddiesbackend.report.domain.category.exception.UnmodifiableCategoryException;
import com.prography.budgetbuddiesbackend.report.domain.category.repository.CategoryRepository;
import com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.entity.ConsumptionGoal;
import com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.service.ConsumptionGoalService;
import com.prography.budgetbuddiesbackend.report.domain.expense.service.ExpenseService;
import com.prography.budgetbuddiesbackend.report.domain.user.entity.User;
import com.prography.budgetbuddiesbackend.report.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class CategoryService {
	private final CategoryRepository categoryRepository;
	private final CategoryMapper mapper;

	private final UserService userService;
	private final ConsumptionGoalService consumptionGoalService;
	private final ExpenseService expenseService;

	/**
	 * 사용자의 요청을 바탕으로 새 카테고리를 생성합니다.
	 * 사용자 카테고리 중 중복된 카테고리가 존재할 경우 예외를 반환합니다.
	 * 생성된 카테고리를 기반으로 해당 월의 초기 소비목표도 함께 생성합니다.
	 */
	public void registerCategory(RegisterCategoryRequest request, Long userId) {
		validateCategoryCreatable(request.name(), userId);

		User user = userService.findById(userId);
		Category newCategory = mapper.registerCategoryRequestToEntity(request, user);
		newCategory = categoryRepository.save(newCategory);

		ConsumptionGoal newConsumptionGoal = newCategory.createInitialGoal(YearMonth.now());
		consumptionGoalService.save(newConsumptionGoal);
	}

	private void validateCategoryCreatable(String categoryName, Long userId) {
		Set<String> userCategoryNames = categoryRepository.findAllCategoryNamesByUserIdOrType(userId, DEFAULT);
		if (userCategoryNames.contains(categoryName)) {
			throw new DuplicateCategoryNameException();
		}
	}

	/**
	 * 사용자 요청에 따라 해당 사용자의 카테고리를 삭제합니다.
	 *
	 * - 기본 카테고리이거나 사용자 소유가 아닌 경우 예외를 발생시킵니다.
	 * - 관련된 소비내역의 카테고리 참조를 제거합니다 (category_id = NULL).
	 * - 관련된 소비목표를 삭제합니다.
	 * - 마지막으로 카테고리를 소프트 삭제합니다.
	 */
	public void deleteCategory(Long categoryId, Long userId) {
		Category category = findById(categoryId);
		validateCategoryModifiable(userId, category);

		// 카테고리가 삭제됨에 따라서, 소비목표도 함께 삭제되어야 함, 그리고 소비내역 또한 같이 삭제되어야 함.
		consumptionGoalService.deleteAllByCategory(category);

		expenseService.clearCategoryReference(category);

		categoryRepository.delete(category);
	}

	private void validateCategoryModifiable(Long userId, Category category) {
		if (DEFAULT.equals(category.getType()) || !category.getUser().getId().equals(userId)) {
			throw new UnmodifiableCategoryException();
		}
	}

	public List<UserCategoryResponse> findUserCategories(Long userId) {
		List<Category> userCategories = categoryRepository.findUserCategoriesByUserIdOrType(userId, DEFAULT);

		return userCategories.stream().map(mapper::entityToUserCategoryResponse).toList();
	}

	public Category findById(Long categoryId) {
		return categoryRepository.findById(categoryId).orElseThrow(NotFoundCategoryException::new);
	}
}
