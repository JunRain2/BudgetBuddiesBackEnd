package com.prography.budgetbuddiesbackend.report.adapter.out.persistence;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.prography.budgetbuddiesbackend.report.adapter.out.exception.NotFoundCategoryException;
import com.prography.budgetbuddiesbackend.report.application.port.out.category.CreateCategoryCommand;
import com.prography.budgetbuddiesbackend.report.application.port.out.category.CreateCategoryPort;
import com.prography.budgetbuddiesbackend.report.application.port.out.category.DeleteCategoryPort;
import com.prography.budgetbuddiesbackend.report.application.port.out.category.FindCategoryPort;
import com.prography.budgetbuddiesbackend.report.application.port.out.category.FindUserCategoryPort;
import com.prography.budgetbuddiesbackend.report.domain.Category;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
class CategoryPersistenceAdapter implements CreateCategoryPort, DeleteCategoryPort, FindCategoryPort,
	FindUserCategoryPort {

	private final CategoryRepository categoryRepository;
	private final CategoryMapper mapper;

	@Override
	public Category createCategory(CreateCategoryCommand command) {
		CategoryEntity categoryEntity = mapper.createCategoryCommandToEntity(command);
		categoryEntity = categoryRepository.save(categoryEntity);

		return mapper.entityToCategory(categoryEntity);
	}

	@Override
	public void deleteCategory(Category category) {
		categoryRepository.deleteById(category.getCategoryId());
	}

	@Override
	public List<Category> findUserAndDefaultCategory(Long userId) {
		List<CategoryEntity> categoryEntityList = categoryRepository.findAllByUserIdOrIsDefaultTrue(userId);

		return categoryEntityList.stream().map(mapper::entityToCategory).toList();
	}

	@Override
	public Category findUserCategory(Long userId, Long categoryId) {
		CategoryEntity categoryEntity = categoryRepository.findByUserIdAndId(userId, categoryId)
			.orElseThrow(NotFoundCategoryException::new);

		return mapper.entityToCategory(categoryEntity);
	}

	@Override
	public Category findCategory(Long categoryId) {
		CategoryEntity categoryEntity = categoryRepository.findById(categoryId)
			.orElseThrow(NotFoundCategoryException::new);

		return mapper.entityToCategory(categoryEntity);
	}

	@Override
	public Set<String> findUserAndDefaultCategoryName(Long userId) {
		return categoryRepository.findNameByUserIdOrIsDefaultTrue(userId);
	}
}
