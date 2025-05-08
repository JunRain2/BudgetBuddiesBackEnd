package com.prography.budgetbuddiesbackend.report.application.port.out;

import java.util.List;
import java.util.Set;

import com.prography.budgetbuddiesbackend.report.domain.Category;

public interface FindUserCategoryPort {
	Set<String> findUserCategoryNames(Long userId);

	List<Category> findUserCategories(Long userId);

}
