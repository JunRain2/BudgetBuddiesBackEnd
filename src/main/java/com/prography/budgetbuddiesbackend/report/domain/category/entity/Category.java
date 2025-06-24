package com.prography.budgetbuddiesbackend.report.domain.category.entity;

import static com.prography.budgetbuddiesbackend.report.domain.category.entity.CategoryType.*;

import java.time.YearMonth;

import com.prography.budgetbuddiesbackend.common.entity.BaseEntity;
import com.prography.budgetbuddiesbackend.report.domain.category.exception.UnmodifiableCategoryException;
import com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.entity.ConsumptionGoal;
import com.prography.budgetbuddiesbackend.user.entity.UserId;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
	@EmbeddedId
	private CategoryId id;

	@Embedded
	@NotNull
	@AttributeOverride(name = "id", column = @Column(name = "user_id", nullable = false))
	private UserId userId;

	@NotNull
	@Column(name = "type", nullable = false)
	@Enumerated(EnumType.STRING)
	private CategoryType type;

	@Size(max = 20)
	@NotNull
	@Column(name = "name", nullable = false, length = 20)
	private String name;

	private Category(CategoryId id, UserId userId, String name) {
		this.id = id;
		this.userId = userId;
		this.name = name;
		this.type = CategoryType.CUSTOM;
	}

	public static Category of(UserId userId, String name) {
		return new Category(CategoryId.generate(), userId, name);
	}

	public ConsumptionGoal createInitialGoal(YearMonth yearMonth) {
		final Integer initialCap = 200000;
		return ConsumptionGoal.of(this.userId, this, initialCap, yearMonth);
	}

	public void validateModifiable(UserId userId) {
		if (DEFAULT.equals(this.type) || !userId.equals(this.userId)) {
			throw new UnmodifiableCategoryException();
		}
	}
}