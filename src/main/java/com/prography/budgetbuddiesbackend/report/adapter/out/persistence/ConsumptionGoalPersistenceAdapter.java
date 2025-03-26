package com.prography.budgetbuddiesbackend.report.adapter.out.persistence;

import org.springframework.stereotype.Repository;

import com.prography.budgetbuddiesbackend.report.adapter.out.NotFoundCategoryException;
import com.prography.budgetbuddiesbackend.report.adapter.out.NotFoundUserException;
import com.prography.budgetbuddiesbackend.report.application.port.out.consumptionGoal.CreateConsumptionGoalCommand;
import com.prography.budgetbuddiesbackend.report.application.port.out.consumptionGoal.CreateConsumptionGoalPort;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ConsumptionGoalPersistenceAdapter implements CreateConsumptionGoalPort {

	private final ConsumptionGoalRepository consumptionGoalRepository;
	private final UserRepository userRepository;
	private final CategoryRepository categoryRepository;
	private final ConsumptionGoalMapper mapper;

	@Override
	public void createConsumptionGoal(CreateConsumptionGoalCommand command) {
		CategoryEntity categoryEntity = categoryRepository.findById(command.categoryId()).orElseThrow(
			NotFoundCategoryException::new);
		UserEntity userEntity = userRepository.findById(command.userId()).orElseThrow(NotFoundUserException::new);

		ConsumptionGoalEntity consumptionGoalEntity = new ConsumptionGoalEntity(command.cap(), userEntity,
			categoryEntity);
	}
}
