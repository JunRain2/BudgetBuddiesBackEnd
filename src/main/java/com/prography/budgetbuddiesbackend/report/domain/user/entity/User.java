package com.prography.budgetbuddiesbackend.report.domain.user.entity;

import com.prography.budgetbuddiesbackend.common.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "user", schema = "budgetbuddies")
public class User extends BaseEntity {
	@Id
	@Column(name = "id", nullable = false)
	private Long id;
}