package com.prography.budgetbuddiesbackend.report.application.port.out;

import com.prography.budgetbuddiesbackend.report.domain.Category;

public interface RegisterCategoryPort {
	Category registerCategory(Category category, Long userId);
}
