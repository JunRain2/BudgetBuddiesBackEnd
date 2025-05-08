package com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.entity;

import java.time.YearMonth;

import com.prography.budgetbuddiesbackend.common.entity.BaseEntity;
import com.prography.budgetbuddiesbackend.report.domain.category.entity.Category;
import com.prography.budgetbuddiesbackend.report.domain.user.entity.User;

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
public class ConsumptionGoal extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "category_id", nullable = false)
	private Category category;

	@NotNull
	@Column(name = "cap", nullable = false)
	private Integer cap;

	@NotNull
	@Column(name = "goal_month", nullable = false, columnDefinition = "CHAR(7)")
	private YearMonth goalMonth;

	private ConsumptionGoal(User user, Category category, Integer cap, YearMonth goalMonth) {
		this.user = user;
		this.category = category;
		this.cap = cap;
		this.goalMonth = goalMonth;
	}

	public static ConsumptionGoal of(User user, Category category, Integer cap, YearMonth yearMonth) {
		return new ConsumptionGoal(user, category, cap, yearMonth);
	}
}