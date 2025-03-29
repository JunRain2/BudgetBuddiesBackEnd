package com.prography.budgetbuddiesbackend.report.adapter.out.persistence;

import org.springframework.stereotype.Component;

import com.prography.budgetbuddiesbackend.report.domain.ConsumptionGoal;

@Component
class ConsumptionGoalMapper {

	public ConsumptionGoalEntity consumptionGoalToEntity(ConsumptionGoal consumptionGoal, Long userId) {
		return new ConsumptionGoalEntity(
			null,
			userId,
			consumptionGoal.getCategoryId(),
			consumptionGoal.getCap().value(),
			consumptionGoal.getGoalAt()
		);
	}
}
