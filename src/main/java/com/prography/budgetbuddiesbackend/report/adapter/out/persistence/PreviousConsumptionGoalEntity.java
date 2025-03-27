package com.prography.budgetbuddiesbackend.report.adapter.out.persistence;

import java.time.LocalDate;

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
@Table(name = "previous_consumption_goal")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PreviousConsumptionGoalEntity extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private UserEntity user;

	private String categoryName;

	@Column(nullable = false)
	private int cap;

	private int consumption;

	private LocalDate goalAt;
}

