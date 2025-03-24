package com.prography.budgetbuddiesbackend.category.application.port.out;

import com.prography.budgetbuddiesbackend.category.domain.Category;

public interface RegisterCategoryPort {
	Category registerCategory(RegisterCategoryCommand categoryCommand);
}
