package com.prography.budgetbuddiesbackend.report.application;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prography.budgetbuddiesbackend.report.application.exception.DuplicateCategoryNameException;
import com.prography.budgetbuddiesbackend.report.application.exception.UnmodifiableCategoryException;
import com.prography.budgetbuddiesbackend.report.application.port.in.CategoryUseCase;
import com.prography.budgetbuddiesbackend.report.application.port.in.RegisterCategoryCommand;
import com.prography.budgetbuddiesbackend.report.application.port.in.UserCategoryResponse;
import com.prography.budgetbuddiesbackend.report.application.port.out.DeleteCategoryPort;
import com.prography.budgetbuddiesbackend.report.application.port.out.FindCategoryPort;
import com.prography.budgetbuddiesbackend.report.application.port.out.FindUserCategoryPort;
import com.prography.budgetbuddiesbackend.report.application.port.out.RegisterCategoryPort;
import com.prography.budgetbuddiesbackend.report.domain.Category;
import com.prography.budgetbuddiesbackend.report.domain.enums.CategoryType;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class CategoryService implements CategoryUseCase {
	private final RegisterCategoryPort registerCategoryPort;
	private final FindUserCategoryPort findUserCategoryPort;
	private final FindCategoryPort findCategoryPort;
	private final DeleteCategoryPort deleteCategoryPort;

	private final CategoryMapper mapper;

	@Override
	public void registerCategory(RegisterCategoryCommand command) {
		validateCategoryCreatable(command);

		Category newCategory = mapper.registerCategoryCommandToDomain(command);
		registerCategoryPort.registerCategory(newCategory);
	}

	private void validateCategoryCreatable(RegisterCategoryCommand command) {
		Set<String> userCategoryNames = findUserCategoryPort.findUserCategoryNames(command.userId());
		if (userCategoryNames.contains(command.name())) {
			throw new DuplicateCategoryNameException();
		}
	}

	@Override
	public void deleteCategory(Long categoryId, Long userId) {
		Category category = findCategoryPort.findById(categoryId);
		validateCategoryModifiable(userId, category);

		deleteCategoryPort.deleteCategory(category);
	}

	private void validateCategoryModifiable(Long userId, Category category) {
		if (category.getType() == CategoryType.DEFAULT || !category.getUserId().equals(userId)) {
			throw new UnmodifiableCategoryException();
		}
	}

	@Override
	public List<UserCategoryResponse> findUserCategories(Long userId) {
		List<Category> userCategories = findUserCategoryPort.findUserCategories(userId);

		return userCategories.stream().map(mapper::domainToUserCategoryResponse).toList();
	}
}
