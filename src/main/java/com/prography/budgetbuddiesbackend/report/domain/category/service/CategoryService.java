package com.prography.budgetbuddiesbackend.report.domain.category.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prography.budgetbuddiesbackend.report.domain.category.dto.request.RegisterCategoryRequest;
import com.prography.budgetbuddiesbackend.report.domain.category.dto.response.UserCategoryResponse;
import com.prography.budgetbuddiesbackend.report.domain.category.entity.Category;
import com.prography.budgetbuddiesbackend.report.domain.category.exception.DuplicateCategoryNameException;
import com.prography.budgetbuddiesbackend.report.domain.category.exception.UnmodifiableCategoryException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class CategoryService {
	private final CategoryRepository categoryRepository;

	private final CategoryMapper mapper;

	public void registerCategory(RegisterCategoryRequest request, Long userId) {
		validateCategoryCreatable(request.name(), userId);

		categoryRepository.registerCategory(request, userId);
	}

	private void validateCategoryCreatable(String categoryName, Long userId) {
		Set<String> userCategoryNames = categoryRepository.findUserCategoryNames(userId);
		if (userCategoryNames.contains(categoryName)) {
			throw new DuplicateCategoryNameException();
		}
	}

	public void deleteCategory(Long categoryId, Long userId) {
		Category category = categoryRepository.findById(categoryId);
		validateCategoryModifiable(userId, category);

		categoryRepository.deleteCategory(category);
	}

	private void validateCategoryModifiable(Long userId, Category category) {
		if (Boolean.TRUE.equals(category.getIsDefault()) || !category.getUser().getId().equals(userId)) {
			throw new UnmodifiableCategoryException();
		}
	}

	public List<UserCategoryResponse> findUserCategories(Long userId) {
		List<Category> userCategories = categoryRepository.findUserCategories(userId);

		return userCategories.stream().map(mapper::entityToUserCategoryResponse).toList();
	}
}
