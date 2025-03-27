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
	private String categoryName;
	private LocalDate goalAt;
	private Money cap;
	private Money spendingMoney;

	public int getResidualAmount() {
		return this.cap.value() - this.spendingMoney.value();
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
