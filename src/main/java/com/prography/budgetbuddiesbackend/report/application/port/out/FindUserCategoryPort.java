package com.prography.budgetbuddiesbackend.report.application.port.out;

import java.util.Set;

public interface FindUserCategoryPort {
	Set<String> userCategoryNames(Long userId);
}
