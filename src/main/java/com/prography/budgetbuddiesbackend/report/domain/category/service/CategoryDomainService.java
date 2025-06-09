package com.prography.budgetbuddiesbackend.report.domain.category.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prography.budgetbuddiesbackend.report.domain.category.entity.Category;
import com.prography.budgetbuddiesbackend.report.domain.category.entity.CategoryType;
import com.prography.budgetbuddiesbackend.report.domain.category.exception.NotFoundCategoryException;
import com.prography.budgetbuddiesbackend.report.domain.category.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryDomainService {
	private final CategoryRepository categoryRepository;

	public Category save(Category category) {
		return categoryRepository.save(category);
	}

	public void delete(Category category) {
		categoryRepository.delete(category);
	}

	public Category findById(Long id) {
		return categoryRepository.findById(id).orElseThrow(NotFoundCategoryException::new);
	}

	public List<Category> findCategoriesByUser(Long userId) {
		return categoryRepository.findUserCategoriesByUserIdOrType(userId, CategoryType.DEFAULT);
	}

	public Set<String> findCategoryNamesByUser(Long userId) {
		return categoryRepository.findAllCategoryNamesByUserIdOrType(userId, CategoryType.DEFAULT);
	}
} 