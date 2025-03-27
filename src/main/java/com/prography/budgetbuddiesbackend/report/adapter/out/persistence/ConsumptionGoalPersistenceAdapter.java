package com.prography.budgetbuddiesbackend.report.adapter.out.persistence;

import org.springframework.stereotype.Repository;

import com.prography.budgetbuddiesbackend.report.adapter.out.persistence.exception.NotFoundCategoryException;
import com.prography.budgetbuddiesbackend.report.adapter.out.persistence.exception.NotFoundUserException;
import com.prography.budgetbuddiesbackend.report.application.port.out.consumptionGoal.CreateConsumptionGoalPort;
import com.prography.budgetbuddiesbackend.report.domain.ConsumptionGoal;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ConsumptionGoalPersistenceAdapter implements CreateConsumptionGoalPort {

	private final ConsumptionGoalRepository consumptionGoalRepository;
	private final UserRepository userRepository;
	private final CategoryRepository categoryRepository;
	private final ConsumptionGoalMapper mapper;

	@Override
	public void createConsumptionGoal(Long userId, ConsumptionGoal consumptionGoal) {
		CategoryEntity categoryEntity = categoryRepository.findById(consumptionGoal.getCategoryId()).orElseThrow(
			NotFoundCategoryException::new);
		UserEntity userEntity = userRepository.findById(userId).orElseThrow(NotFoundUserException::new);

		ConsumptionGoalEntity consumptionGoalEntity = mapper.consumptionGoalToConsumptionGoalEntity(consumptionGoal,
			userEntity, categoryEntity);
	}
}
