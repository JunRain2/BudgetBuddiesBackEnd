package com.prography.budgetbuddiesbackend.report.domain;

import java.time.YearMonth;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ConsumptionGoal {
	private Long id;
	private Long categoryId;
	private Integer cap;
	private YearMonth goalMonth;

	public static ConsumptionGoal of(Long id, Long categoryId, Integer cap, YearMonth goalMonth) {
		return new ConsumptionGoal(id, categoryId, cap, goalMonth);
	}
}
