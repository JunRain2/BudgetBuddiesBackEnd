package com.prography.budgetbuddiesbackend.report.application;

import java.util.List;

import org.springframework.stereotype.Service;

import com.prography.budgetbuddiesbackend.report.application.port.in.consumptionGoal.RegisterDefaultCategoryConsumptionGoalUseCase;
import com.prography.budgetbuddiesbackend.report.application.port.out.category.FindUserCategoryPort;
import com.prography.budgetbuddiesbackend.report.application.port.out.consumptionGoal.CreateConsumptionGoalPort;
import com.prography.budgetbuddiesbackend.report.domain.Category;
import com.prography.budgetbuddiesbackend.report.domain.ConsumptionGoal;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegisterUserConsumptionGoalService implements RegisterDefaultCategoryConsumptionGoalUseCase {

	private final FindUserCategoryPort findUserCategoryPort;
	private final CreateConsumptionGoalPort createConsumptionGoalPort;

	@Override
	public void registerConsumptionGoal(Long userId) {
		List<Category> categoryList = findUserCategoryPort.findUserAndDefaultCategory(userId);

		List<ConsumptionGoal> consumptionGoalList = categoryList.stream().map(Category::createConsumptionGoal).toList();
		createConsumptionGoalPort.createConsumptionGoalList(userId, consumptionGoalList);
	}
}
