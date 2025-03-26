package com.prography.budgetbuddiesbackend.report.adapter.out.persistence;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.prography.budgetbuddiesbackend.report.adapter.out.NotFoundCategoryException;
import com.prography.budgetbuddiesbackend.report.adapter.out.NotFoundUserException;
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
		UserEntity userEntity = userRepository.findById(command.userId()).orElseThrow(NotFoundUserException::new);
		CategoryEntity categoryEntity = new CategoryEntity(command.name(), false, userEntity);
		return mapper.categoryEntityToCategory(categoryRepository.save(categoryEntity));
	}

	@Override
	public void deleteCategory(Category category) {
		categoryRepository.deleteById(category.getCategoryId());
	}

	@Override
	public List<Category> findUserAndDefaultCategory(Long userId) {
		UserEntity userEntity = userRepository.findById(userId).orElseThrow(NotFoundUserException::new);
		List<CategoryEntity> categoryEntityList = categoryRepository.findByUserOrIsDefault(userEntity);

		return categoryEntityList.stream().map(mapper::categoryEntityToCategory).toList();
	}

	@Override
	public Category findUserCategory(Long userId, Long categoryId) {
		UserEntity userEntity = userRepository.findById(userId).orElseThrow(NotFoundUserException::new);
		CategoryEntity categoryEntity = categoryRepository.findByIdAndUser(categoryId, userEntity)
			.orElseThrow(NotFoundCategoryException::new);

		return mapper.categoryEntityToCategory(categoryEntity);
	}
}
