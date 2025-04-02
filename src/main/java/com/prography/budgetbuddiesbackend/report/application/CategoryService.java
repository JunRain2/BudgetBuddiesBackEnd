package com.prography.budgetbuddiesbackend.report.application;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prography.budgetbuddiesbackend.report.application.port.in.category.CategoryUseCase;
import com.prography.budgetbuddiesbackend.report.application.port.in.category.DeleteCategoryCommand;
import com.prography.budgetbuddiesbackend.report.application.port.in.category.FindUserCategoryResult;
import com.prography.budgetbuddiesbackend.report.application.port.in.category.RegisterCategoryCommand;
import com.prography.budgetbuddiesbackend.report.application.port.out.category.CreateCategoryCommand;
import com.prography.budgetbuddiesbackend.report.application.port.out.category.CreateCategoryPort;
import com.prography.budgetbuddiesbackend.report.application.port.out.category.DeleteCategoryPort;
import com.prography.budgetbuddiesbackend.report.application.port.out.category.FindCategoryPort;
import com.prography.budgetbuddiesbackend.report.application.port.out.consumptionGoal.CreateConsumptionGoalPort;
import com.prography.budgetbuddiesbackend.report.domain.Category;
import com.prography.budgetbuddiesbackend.report.domain.ConsumptionGoal;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
class CategoryService implements CategoryUseCase {
	private final FindCategoryPort findCategoryPort;
	private final CreateCategoryPort createCategoryPort;
	private final DeleteCategoryPort deleteCategoryPort;
	private final CreateConsumptionGoalPort createConsumptionGoalPort;

	@Override
	public List<FindUserCategoryResult> findUserCategoryList(Long userId) {
		List<Category> categoryList = findCategoryPort.findUserAndDefaultCategory(userId);
		List<FindUserCategoryResult> results = categoryList.stream().map(c ->
			new FindUserCategoryResult(c.getCategoryId(), c.getName(), c.isDefault())).toList();

		return results;
	}

	@Override
	public void registerCategory(RegisterCategoryCommand command) {
		CreateCategoryCommand createCommand = new CreateCategoryCommand(command.userId(), command.categoryName());
		Category category = createCategoryPort.createCategory(createCommand);

		Set<String> categoryNames = findCategoryPort.findUserAndDefaultCategoryName(command.userId());
		category.checkNameDuplicate(categoryNames);

		ConsumptionGoal consumptionGoal = category.createConsumptionGoal();
		createConsumptionGoalPort.createConsumptionGoal(command.userId(), consumptionGoal);
	}

	@Override
	public void deleteCategory(DeleteCategoryCommand command) {
		Category category = findCategoryPort.findUserCategory(command.userId(), command.categoryId());
		deleteCategoryPort.deleteCategory(category);
	}
}
