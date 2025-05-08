package com.prography.budgetbuddiesbackend.report.adapter.out;

import java.time.YearMonth;

import com.prography.budgetbuddiesbackend.common.entity.BaseEntity;
import com.prography.budgetbuddiesbackend.report.adapter.out.category.CategoryEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "consumption_goal", schema = "budgetbuddies")
public class ConsumptionGoalEntity extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private UserEntity user;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "category_id", nullable = false)
	private CategoryEntity category;

	@NotNull
	@Column(name = "cap", nullable = false)
	private Integer cap;

	@NotNull
	@Column(name = "goal_month", nullable = false, columnDefinition = "CHAR(7)")
	private YearMonth goalMonth;

	private ConsumptionGoalEntity(UserEntity user, CategoryEntity category, Integer cap, YearMonth goalMonth) {
		this.user = user;
		this.category = category;
		this.cap = cap;
		this.goalMonth = goalMonth;
	}

	public static ConsumptionGoalEntity of(UserEntity user, CategoryEntity category, Integer cap, YearMonth yearMonth) {
		return new ConsumptionGoalEntity(user, category, cap, yearMonth);
	}
}