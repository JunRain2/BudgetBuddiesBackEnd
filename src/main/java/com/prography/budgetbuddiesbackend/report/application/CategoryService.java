package com.prography.budgetbuddiesbackend.report.application;

import java.util.List;

import com.prography.budgetbuddiesbackend.report.adapter.in.UserCategoryListResponse;
import com.prography.budgetbuddiesbackend.report.application.port.in.category.CategoryUseCase;
import com.prography.budgetbuddiesbackend.report.application.port.in.category.DeleteCategoryCommand;
import com.prography.budgetbuddiesbackend.report.application.port.in.category.RegisterCategoryCommand;
import com.prography.budgetbuddiesbackend.report.application.port.out.category.CreateCategoryCommand;
import com.prography.budgetbuddiesbackend.report.application.port.out.category.CreateCategoryPort;
import com.prography.budgetbuddiesbackend.report.application.port.out.category.DeleteCategoryPort;
import com.prography.budgetbuddiesbackend.report.application.port.out.category.FindCategoryPort;
import com.prography.budgetbuddiesbackend.report.application.port.out.consumptionGoal.CreateConsumptionGoalCommand;
import com.prography.budgetbuddiesbackend.report.application.port.out.consumptionGoal.CreateConsumptionGoalPort;
import com.prography.budgetbuddiesbackend.report.domain.Category;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CategoryService implements CategoryUseCase {
	private final FindCategoryPort findCategoryPort;
	private final CreateCategoryPort createCategoryPort;
	private final DeleteCategoryPort deleteCategoryPort;
	private final CreateConsumptionGoalPort createConsumptionGoalPort;

	@Override
	public UserCategoryListResponse findUserCategoryList(Long userId) {
		List<Category> categoryList = findCategoryPort.findUserAndDefaultCategory(userId);

		return new UserCategoryListResponse(categoryList);
	}

	@Override
	public void registerCategory(RegisterCategoryCommand command) {
		CreateCategoryCommand createCommand = new CreateCategoryCommand(command.userId(), command.categoryName());
		Category category = createCategoryPort.createCategory(createCommand);

		createConsumptionGoal(command, category);
	}

	private void createConsumptionGoal(RegisterCategoryCommand command, Category category) {
		final int DEFAULT_CAP = 200000;

		CreateConsumptionGoalCommand consumptionGoalCommand = new CreateConsumptionGoalCommand(command.userId(),
			category.getCategoryId(), DEFAULT_CAP);
		createConsumptionGoalPort.createConsumptionGoal(consumptionGoalCommand);
	}

	@Override
	public void deleteCategory(DeleteCategoryCommand command) {
		Category category = findCategoryPort.findCategory(command.userId(), command.categoryId());
		deleteCategoryPort.deleteCategory(category);
	}
}
