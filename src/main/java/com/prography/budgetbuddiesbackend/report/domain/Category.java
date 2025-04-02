package com.prography.budgetbuddiesbackend.report.domain;

import java.time.LocalDate;
import java.util.Set;

import com.prography.budgetbuddiesbackend.common.vo.Money;
import com.prography.budgetbuddiesbackend.report.domain.exception.DuplicateCategoryNameException;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Category {
	private Long categoryId;
	private String name;
	private boolean isDefault;

	public ConsumptionGoal createConsumptionGoal() {
		final int DEFAULT_CAP = 200000;

		LocalDate now = LocalDate.now();
		now = now.withDayOfMonth(1);

		return new ConsumptionGoal(null, categoryId, now, new Money(DEFAULT_CAP));
	}

	public void checkNameDuplicate(Set<String> categoryNames) {
		if (categoryNames.contains(this.name)) {
			throw new DuplicateCategoryNameException();
		}
	}
}
