package com.prography.budgetbuddiesbackend.report.adapter.out.persistence;

import org.springframework.stereotype.Component;

import com.prography.budgetbuddiesbackend.report.domain.ConsumptionGoal;

@Component
public class ConsumptionGoalMapper {
	public ConsumptionGoalEntity consumptionGoalToConsumptionGoalEntity(ConsumptionGoal consumptionGoal,
		UserEntity userEntity, CategoryEntity categoryEntity) {
		return new ConsumptionGoalEntity(consumptionGoal.getCap().value(), userEntity, categoryEntity);
	}
}
