package com.prography.budgetbuddiesbackend.report.domain.category.entity;

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
public class CategoryId implements Serializable {
	@Column(name = "id", nullable = false)
	private UUID id;

	private CategoryId(UUID id) {
		this.id = id;
	}

	public static CategoryId generate() {
		return new CategoryId(UuidCreator.getTimeOrderedEpoch());
	}

	public static CategoryId of(UUID id) {
		return new CategoryId(id);
	}
}
