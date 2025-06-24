package com.prography.budgetbuddiesbackend.user.entity;

import com.prography.budgetbuddiesbackend.common.entity.BaseEntity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "user", schema = "budgetbuddies")
public class User extends BaseEntity {
	@EmbeddedId
	private UserId id;

	private User(UserId id) {
		this.id = id;
	}

	public static User of() {
		return new User(UserId.generate());
	}
}