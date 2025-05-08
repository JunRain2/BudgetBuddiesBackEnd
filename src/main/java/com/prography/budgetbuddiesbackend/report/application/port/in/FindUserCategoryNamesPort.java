package com.prography.budgetbuddiesbackend.report.application.port.in;

import java.util.Set;

public interface FindUserCategoryNamesPort {
	Set<String> userCategoryNames(Long userId);
}
