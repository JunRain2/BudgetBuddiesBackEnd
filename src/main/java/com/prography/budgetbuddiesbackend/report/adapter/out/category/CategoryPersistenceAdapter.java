package com.prography.budgetbuddiesbackend.report.adapter.out.category;

import java.util.Set;

import org.springframework.stereotype.Repository;

import com.prography.budgetbuddiesbackend.report.adapter.out.UserEntity;
import com.prography.budgetbuddiesbackend.report.adapter.out.UserRepository;
import com.prography.budgetbuddiesbackend.report.application.port.in.FindUserCategoryNamesPort;
import com.prography.budgetbuddiesbackend.report.application.port.out.RegisterCategoryPort;
import com.prography.budgetbuddiesbackend.report.domain.Category;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class CategoryPersistenceAdapter implements RegisterCategoryPort, FindUserCategoryNamesPort {
	private final UserRepository userRepository;
	private final CategoryRepository categoryRepository;

	private final CategoryMapper mapper;

	@Override
	public Category registerCategory(Category category, Long userId) {
		UserEntity user = userRepository.getReferenceById(userId);

		CategoryEntity newCategory = mapper.entityFromDomain(category, user);
		newCategory = categoryRepository.save(newCategory);

		return mapper.domainFromEntity(newCategory);
	}

	@Override
	public Set<String> userCategoryNames(Long userId) {
		return categoryRepository.findAllCategoryNamesByUserIdOrDefault(userId);
	}
}
