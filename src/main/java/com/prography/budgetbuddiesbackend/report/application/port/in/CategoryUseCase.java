package com.prography.budgetbuddiesbackend.report.application.port.in;

import java.util.List;

public interface CategoryUseCase {
	void registerCategory(RegisterCategoryCommand command);

	void deleteCategory(Long categoryId, Long userId);

	List<UserCategoryResponse> findUserCategories(Long userId);
}
