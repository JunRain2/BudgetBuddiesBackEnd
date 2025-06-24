package com.prography.budgetbuddiesbackend.report.domain.expense.entity;

import java.io.Serializable;
import java.util.UUID;

import com.github.f4b6a3.uuid.UuidCreator;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(doNotUseGetters = true)
public class ExpenseId implements Serializable {

	@Column(name = "id", nullable = false)
	private UUID id;

	private ExpenseId(UUID id) {
		this.id = id;
	}

	public static ExpenseId generate() {
		return new ExpenseId(UuidCreator.getTimeOrderedEpoch());
	}

	public static ExpenseId of(UUID id){
		return new ExpenseId(id);
	}
}
