package com.prography.budgetbuddiesbackend.report.adapter.out;

import com.prography.budgetbuddiesbackend.common.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "category", schema = "budgetbuddies")
public class CategoryEntity extends BaseEntity {
	@Id
	@Column(name = "id", nullable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private UserEntity user;

	@NotNull
	@Column(name = "is_default", nullable = false)
	private Boolean isDefault = false;

	@Size(max = 20)
	@NotNull
	@Column(name = "name", nullable = false, length = 20)
	private String name;

	private CategoryEntity(UserEntity user, Boolean isDefault, String name) {
		this.user = user;
		this.isDefault = isDefault;
		this.name = name;
	}

	public static CategoryEntity of(UserEntity user, Boolean isDefault, String name) {
		return new CategoryEntity(user, isDefault, name);
	}
}