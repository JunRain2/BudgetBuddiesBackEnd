package com.prography.budgetbuddiesbackend.report.adapter.out.persistence;

import org.springframework.stereotype.Component;

import com.prography.budgetbuddiesbackend.common.vo.Money;
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

	public ConsumptionGoal entityToConsumptionGoal(ConsumptionGoalEntity consumptionGoalEntity) {
		return new ConsumptionGoal(consumptionGoalEntity.getId(),
			consumptionGoalEntity.getCategoryId(),
			consumptionGoalEntity.getGoalAt(),
			new Money(consumptionGoalEntity.getCap()));
	}
}
