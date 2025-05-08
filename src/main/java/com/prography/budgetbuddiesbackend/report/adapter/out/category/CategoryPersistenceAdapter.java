package com.prography.budgetbuddiesbackend.report.adapter.out.category;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.prography.budgetbuddiesbackend.report.adapter.out.UserEntity;
import com.prography.budgetbuddiesbackend.report.adapter.out.UserRepository;
import com.prography.budgetbuddiesbackend.report.adapter.out.category.exception.NotFoundCategoryException;
import com.prography.budgetbuddiesbackend.report.application.port.out.DeleteCategoryPort;
import com.prography.budgetbuddiesbackend.report.application.port.out.FindCategoryPort;
import com.prography.budgetbuddiesbackend.report.application.port.out.FindUserCategoryPort;
import com.prography.budgetbuddiesbackend.report.application.port.out.RegisterCategoryPort;
import com.prography.budgetbuddiesbackend.report.domain.Category;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class CategoryPersistenceAdapter
	implements RegisterCategoryPort, FindUserCategoryPort, FindCategoryPort, DeleteCategoryPort {

	private final UserRepository userRepository;
	private final CategoryRepository categoryRepository;

	private final CategoryMapper mapper;

	@Override
	public Category registerCategory(Category category) {
		UserEntity user = userRepository.getReferenceById(category.getUserId());

		CategoryEntity newCategory = mapper.domainToEntity(category, user);
		newCategory = categoryRepository.save(newCategory);

		return mapper.entityToDomain(newCategory);
	}

	@Override
	public Set<String> findUserCategoryNames(Long userId) {
		return categoryRepository.findAllCategoryNamesByUserIdOrDefault(userId);
	}

	@Override
	public List<Category> findUserCategories(Long userId) {
		List<CategoryEntity> userCategoryEntities = categoryRepository.findUserCategoriesByUserId(userId);

		return userCategoryEntities.stream().map(mapper::entityToDomain).toList();
	}

	@Override
	public void deleteCategory(Category category) {
		CategoryEntity categoryEntity = categoryRepository.findById(category.getId()).orElseThrow(NotFoundCategoryException::new);
		categoryRepository.delete(categoryEntity);
	}

	@Override
	public Category findById(Long categoryId) {
		CategoryEntity category = categoryRepository.findById(categoryId).orElseThrow(NotFoundCategoryException::new);
		return mapper.entityToDomain(category);
	}
}
