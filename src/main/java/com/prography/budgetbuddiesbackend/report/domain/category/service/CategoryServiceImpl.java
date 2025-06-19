package com.prography.budgetbuddiesbackend.report.domain.category.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prography.budgetbuddiesbackend.report.domain.category.entity.Category;
import com.prography.budgetbuddiesbackend.report.domain.category.entity.CategoryType;
import com.prography.budgetbuddiesbackend.report.domain.category.exception.DuplicateCategoryNameException;
import com.prography.budgetbuddiesbackend.report.domain.category.exception.NotFoundCategoryException;
import com.prography.budgetbuddiesbackend.report.domain.category.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
	private final CategoryRepository categoryRepository;

	public Category save(Category category) {
		validateCategoryCreatable(category);
		return categoryRepository.save(category);
	}

	private void validateCategoryCreatable(Category category) {
		Set<String> userCategoryNames = findCategoryNamesByUser(category.getUserId());
		if (userCategoryNames.contains(category.getName())) {
			throw new DuplicateCategoryNameException();
		}
	}

	private Set<String> findCategoryNamesByUser(Long userId) {
		return categoryRepository.findAllCategoryNamesByUserIdOrType(userId, CategoryType.DEFAULT);
	}

	public void delete(Category category) {
		categoryRepository.delete(category);
	}

	public Category findById(Long id) {
		return categoryRepository.findById(id).orElseThrow(NotFoundCategoryException::new);
	}

	public List<Category> findUserCategories(Long userId) {
		return categoryRepository.findUserCategoriesByUserIdOrType(userId, CategoryType.DEFAULT);
	}

	public Category findUncategorizedCategory() {
		final String uncategorizedCategoryName = "카테고리 없음";
		return categoryRepository.findByName(uncategorizedCategoryName).orElseThrow(NotFoundCategoryException::new);
	}
} 