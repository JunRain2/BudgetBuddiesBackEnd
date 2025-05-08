package com.prography.budgetbuddiesbackend.report.application;

import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prography.budgetbuddiesbackend.report.application.exception.DuplicateCategoryNameException;
import com.prography.budgetbuddiesbackend.report.application.port.in.CategoryUseCase;
import com.prography.budgetbuddiesbackend.report.application.port.in.FindUserCategoryNamesPort;
import com.prography.budgetbuddiesbackend.report.application.port.in.RegisterCategoryCommand;
import com.prography.budgetbuddiesbackend.report.application.port.out.RegisterCategoryPort;
import com.prography.budgetbuddiesbackend.report.domain.Category;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class CategoryService implements CategoryUseCase {
	private final RegisterCategoryPort registerCategoryPort;
	private final FindUserCategoryNamesPort findUserCategoryNamesPort;

	private final CategoryMapper categoryMapper;

	@Override
	public void registerCategory(RegisterCategoryCommand command) {
		validateCategoryCreatable(command);

		Category newCategory = categoryMapper.categoryFromRegisterCategoryCommand(command);
		registerCategoryPort.registerCategory(newCategory, command.userId());
	}

	private void validateCategoryCreatable(RegisterCategoryCommand command) {
		Set<String> userCategoryNames = findUserCategoryNamesPort.userCategoryNames(command.userId());
		if (userCategoryNames.contains(command.name())) {
			throw new DuplicateCategoryNameException();
		}
	}
}
