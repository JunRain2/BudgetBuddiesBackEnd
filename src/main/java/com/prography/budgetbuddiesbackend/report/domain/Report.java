package com.prography.budgetbuddiesbackend.report.domain;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Report {
	private Money totalCap;
	private Money totalConsumption;
	private List<Expense> expenseHistory;
	private List<ConsumptionGoal> consumptionGoalList;
	private LocalDate month;
}
