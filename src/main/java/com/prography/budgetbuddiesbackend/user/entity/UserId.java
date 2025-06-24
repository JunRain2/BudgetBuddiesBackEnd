package com.prography.budgetbuddiesbackend.user.entity;

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
public class UserId implements Serializable {

	@Column(name = "id", nullable = false)
	private UUID id;

	private UserId(UUID id) {
		this.id = id;
	}

	public static UserId generate() {
		return new UserId(UuidCreator.getTimeOrderedEpoch());
	}

	public static UserId of(UUID id) {
		return new UserId(id);
	}
}
