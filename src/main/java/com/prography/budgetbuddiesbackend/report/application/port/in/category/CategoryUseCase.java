package com.prography.budgetbuddiesbackend.report.application.port.in.category;

import java.util.List;

public interface CategoryUseCase {
	List<FindUserCategoryResult> findUserCategoryList(Long userId);

	void registerCategory(RegisterCategoryCommand command);

	void deleteCategory(DeleteCategoryCommand command);
}
