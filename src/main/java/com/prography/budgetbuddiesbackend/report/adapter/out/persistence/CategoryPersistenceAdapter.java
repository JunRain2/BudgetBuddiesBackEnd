package com.prography.budgetbuddiesbackend.report.adapter.out.persistence;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.prography.budgetbuddiesbackend.report.adapter.out.persistence.exception.NotFoundCategoryException;
import com.prography.budgetbuddiesbackend.report.application.port.out.category.CreateCategoryCommand;
import com.prography.budgetbuddiesbackend.report.application.port.out.category.CreateCategoryPort;
import com.prography.budgetbuddiesbackend.report.application.port.out.category.DeleteCategoryPort;
import com.prography.budgetbuddiesbackend.report.application.port.out.category.FindCategoryPort;
import com.prography.budgetbuddiesbackend.report.domain.Category;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
class CategoryPersistenceAdapter implements CreateCategoryPort, DeleteCategoryPort, FindCategoryPort {

	private final CategoryRepository categoryRepository;
	private final UserRepository userRepository;
	private final CategoryMapper mapper;

	@Override
	public Category createCategory(CreateCategoryCommand command) {
		CategoryEntity categoryEntity = mapper.createCategoryCommandToCategoryEntity(command);
		return mapper.categoryEntityToCategory(categoryRepository.save(categoryEntity));
	}

	@Override
	public void deleteCategory(Category category) {
		categoryRepository.deleteById(category.getCategoryId());
	}

	@Override
	public List<Category> findUserAndDefaultCategory(Long userId) {
		List<CategoryEntity> categoryEntityList = categoryRepository.findByUserIdOrIsDefault(userId);

		return categoryEntityList.stream().map(mapper::categoryEntityToCategory).toList();
	}

	@Override
	public Category findUserCategory(Long userId, Long categoryId) {
		CategoryEntity categoryEntity = categoryRepository.findByIdAndUserId(categoryId, userId)
			.orElseThrow(NotFoundCategoryException::new);

		return mapper.categoryEntityToCategory(categoryEntity);
	}
}
