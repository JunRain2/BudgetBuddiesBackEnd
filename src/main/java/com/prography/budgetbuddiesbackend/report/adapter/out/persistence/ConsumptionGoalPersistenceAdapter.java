package com.prography.budgetbuddiesbackend.report.adapter.out.persistence;

import org.springframework.stereotype.Repository;

import com.prography.budgetbuddiesbackend.report.application.port.out.consumptionGoal.CreateConsumptionGoalPort;
import com.prography.budgetbuddiesbackend.report.domain.ConsumptionGoal;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
class ConsumptionGoalPersistenceAdapter implements CreateConsumptionGoalPort {

	private final ConsumptionGoalRepository consumptionGoalRepository;
	private final ConsumptionGoalMapper mapper;

	@Override
	public void createConsumptionGoal(Long userId, ConsumptionGoal consumptionGoal) {
		ConsumptionGoalEntity consumptionGoalEntity = mapper.consumptionGoalToEntity(consumptionGoal, userId);
		consumptionGoalRepository.save(consumptionGoalEntity);
	}
}
