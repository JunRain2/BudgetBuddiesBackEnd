package com.prography.budgetbuddiesbackend.report.application.port.out;

import com.prography.budgetbuddiesbackend.report.domain.Category;

public interface FindCategoryPort {
	Category findById(Long categoryId);
}
