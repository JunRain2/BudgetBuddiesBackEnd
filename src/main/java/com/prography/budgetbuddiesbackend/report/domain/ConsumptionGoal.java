package com.prography.budgetbuddiesbackend.report.domain;

import java.time.LocalDate;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ConsumptionGoal {
	private Long consumptionGoalId;
	private Category category;
	private LocalDate goalAt;
	private Money cap;
	private Money spendingMoney;

	public static ConsumptionGoal of(Long consumptionGoalId, Category category, LocalDate goalAt, Money cap,
		Money spendingMoney) {
		goalAt = goalAt.withDayOfMonth(1);
		return new ConsumptionGoal(consumptionGoalId, category, goalAt, cap, spendingMoney);
	}

	public void modifyCap(int cap) {
		checkThisMont();
		this.cap = new Money(cap);
	}

	private void checkThisMont() {
		LocalDate now = LocalDate.now();
		now = now.withDayOfMonth(1);

		if (!this.goalAt.equals(now)) {
			throw new NotModifyConsumptionGoal();
		}
	}
}
