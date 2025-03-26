package com.prography.budgetbuddiesbackend.report.application.port.out.category;

import com.prography.budgetbuddiesbackend.report.domain.Category;

public interface CreateCategoryPort {
	Category createCategory(CreateCategoryCommand command);
}
