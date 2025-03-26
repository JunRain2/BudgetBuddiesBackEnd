package com.prography.budgetbuddiesbackend.report.application.port.in.category;

import com.prography.budgetbuddiesbackend.report.adapter.in.UserCategoryListResponse;

public interface CategoryUseCase {
	UserCategoryListResponse findUserCategoryList(Long userId);

	void registerCategory(RegisterCategoryCommand command);

	void deleteCategory(DeleteCategoryCommand command);
}
