package com.prography.budgetbuddiesbackend.report.adapter.out.persistence;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

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
@Table(name = "category")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CategoryEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	private Boolean isDefault;

	@ManyToOne(fetch = FetchType.LAZY)
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = "user_id")
	private UserEntity user;

	public CategoryEntity(String name, Boolean isDefault, UserEntity user) {
		this.name = name;
		this.isDefault = isDefault;
		this.user = user;
	}
}
