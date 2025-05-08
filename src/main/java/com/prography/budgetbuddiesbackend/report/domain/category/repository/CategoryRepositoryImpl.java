package com.prography.budgetbuddiesbackend.report.domain.category.repository;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.prography.budgetbuddiesbackend.report.domain.category.dto.request.RegisterCategoryRequest;
import com.prography.budgetbuddiesbackend.report.domain.category.entity.Category;
import com.prography.budgetbuddiesbackend.report.domain.category.exception.NotFoundCategoryException;
import com.prography.budgetbuddiesbackend.report.domain.category.service.CategoryRepository;
import com.prography.budgetbuddiesbackend.report.domain.user.entity.User;
import com.prography.budgetbuddiesbackend.report.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class CategoryRepositoryImpl implements CategoryRepository {

	private final JpaCategoryRepository categoryRepository;
	private final UserRepository userRepository;

	public Category registerCategory(RegisterCategoryRequest request, Long userId) {
		User user = userRepository.getReferenceById(userId);
		Category category = Category.of(user, false, request.name());

		return categoryRepository.save(category);
	}

	public Set<String> findUserCategoryNames(Long userId) {
		return categoryRepository.findAllCategoryNamesByUserIdOrDefault(userId);
	}

	public List<Category> findUserCategories(Long userId) {
		return categoryRepository.findUserCategoriesByUserId(userId);
	}

	public void deleteCategory(Category category) {
		categoryRepository.delete(category);
	}

	public Category findById(Long categoryId) {
		return categoryRepository.findById(categoryId).orElseThrow(NotFoundCategoryException::new);
	}
}
