package com.prography.budgetbuddiesbackend.report.domain.category.entity;

import static com.prography.budgetbuddiesbackend.report.domain.category.entity.CategoryType.*;

import java.time.YearMonth;

import com.prography.budgetbuddiesbackend.common.entity.BaseEntity;
import com.prography.budgetbuddiesbackend.report.domain.category.exception.UnmodifiableCategoryException;
import com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.entity.ConsumptionGoal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@NotNull
	@Column(name = "user_id", nullable = false)
	private Long userId;

	@NotNull
	@Column(name = "type", nullable = false)
	@Enumerated(EnumType.STRING)
	private CategoryType type;

	@Size(max = 20)
	@NotNull
	@Column(name = "name", nullable = false, length = 20)
	private String name;

	private Category(Long userId, String name) {
		this.userId = userId;
		this.name = name;
		this.type = CategoryType.CUSTOM;
	}

	public static Category of(Long userId, String name) {
		return new Category(userId, name);
	}

	public ConsumptionGoal createInitialGoal(YearMonth yearMonth) {
		final Integer initialCap = 200000;
		return ConsumptionGoal.of(this.userId, this, initialCap, yearMonth);
	}

	public void validateModifiable(Long userId) {
		if (DEFAULT.equals(this.type) || !userId.equals(this.userId)) {
			throw new UnmodifiableCategoryException();
		}
	}
}