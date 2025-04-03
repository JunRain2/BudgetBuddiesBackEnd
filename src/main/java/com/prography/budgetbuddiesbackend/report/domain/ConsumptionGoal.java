package com.prography.budgetbuddiesbackend.report.domain;

import java.time.LocalDate;

import com.prography.budgetbuddiesbackend.common.vo.Money;
import com.prography.budgetbuddiesbackend.report.domain.exception.NotModifyConsumptionGoal;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ConsumptionGoal {
	private Long consumptionGoalId;
	private Long categoryId;
	private LocalDate goalAt;
	private Money cap;

	public void modifyCap(int cap) {
		checkThisMonth();
		this.cap = new Money(cap);
	}

	private void checkThisMonth() {
		LocalDate now = LocalDate.now();
		now = now.withDayOfMonth(1);

		if (!this.goalAt.equals(now)) {
			throw new NotModifyConsumptionGoal();
		}
	}
}
