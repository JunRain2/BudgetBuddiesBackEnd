package com.prography.budgetbuddiesbackend.report.domain.category.entity;

import java.time.YearMonth;

import com.prography.budgetbuddiesbackend.common.entity.BaseEntity;
import com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.entity.ConsumptionGoal;
import com.prography.budgetbuddiesbackend.report.domain.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class Category extends BaseEntity {
	@Id
	@Column(name = "id", nullable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@NotNull
	@Column(name = "type", nullable = false)
	@Enumerated(EnumType.STRING)
	private CategoryType type;

	@Size(max = 20)
	@NotNull
	@Column(name = "name", nullable = false, length = 20)
	private String name;

	private Category(User user, String name) {
		this.user = user;
		this.name = name;
		this.type = CategoryType.CUSTOM;
	}

	public static Category of(User user, String name) {
		return new Category(user, name);
	}

	public ConsumptionGoal createInitialGoal(YearMonth yearMonth) {
		final Integer initialCap = 200000;
		return ConsumptionGoal.of(this.user, this, initialCap, yearMonth);
	}
}