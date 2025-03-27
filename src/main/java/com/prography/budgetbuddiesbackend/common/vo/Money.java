package com.prography.budgetbuddiesbackend.common.vo;

public record Money(int value) {
	public Money {
		if (value < 0) {
			throw new NegativeMoneyException();
		}
	}
}
