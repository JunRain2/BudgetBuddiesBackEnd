package com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.entity;

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
public class ConsumptionGoalId implements Serializable {

	@Column(name = "id", nullable = false)
	private UUID id;

	private ConsumptionGoalId(UUID id) {
		this.id = id;
	}

	public static ConsumptionGoalId generate() {
		return new ConsumptionGoalId(UuidCreator.getTimeOrderedEpoch());
	}

	public static ConsumptionGoalId of(UUID id) {
		return new ConsumptionGoalId(id);
	}
}