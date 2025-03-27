package com.prography.budgetbuddiesbackend.report.adapter.out.persistence;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.prography.budgetbuddiesbackend.common.vo.Money;
import com.prography.budgetbuddiesbackend.report.domain.ConsumptionGoal;

@Component
public class ConsumptionGoalMapper {
	public ConsumptionGoal categoryEntityToConsumptionGoal(CategoryEntity categoryEntity,
		int spendingMoney) {
		LocalDate now = LocalDate.now();
		now = now.withDayOfMonth(1);

		return new ConsumptionGoal(categoryEntity.getId(), categoryEntity.getName(), now,
			new Money(categoryEntity.getCap()), new Money(spendingMoney));
	}

	public ConsumptionGoal previousConsumptionGoalEntityToConsumptionGoal(PreviousConsumptionGoalEntity pcge) {
		return new ConsumptionGoal(pcge.getId(), pcge.getCategoryName(), pcge.getGoalAt(), new Money(pcge.getCap()),
			new Money(pcge.getConsumption()));
	}

	public CategoryEntity consumptionGoalToCategoryEntity(UserEntity user, ConsumptionGoal consumptionGoal) {
		return new CategoryEntity(null, consumptionGoal.getCategoryName(), consumptionGoal.getCap().value(), false,
			user);
	}
}
