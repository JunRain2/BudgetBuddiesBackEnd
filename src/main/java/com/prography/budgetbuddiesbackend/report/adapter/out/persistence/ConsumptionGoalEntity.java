package com.prography.budgetbuddiesbackend.report.adapter.out.persistence;

import com.prography.budgetbuddiesbackend.common.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "consumption_goal")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ConsumptionGoalEntity extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private int cap;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private UserEntity user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id")
	private CategoryEntity category;

	public ConsumptionGoalEntity(int cap, UserEntity user, CategoryEntity category) {
		this.cap = cap;
		this.user = user;
		this.category = category;
	}
}

