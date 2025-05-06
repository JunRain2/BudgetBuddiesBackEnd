package com.prography.budgetbuddiesbackend.common.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.validation.constraints.NotNull;

@SQLDelete(sql = "UPDATE #{#entityName} SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

	@NotNull
	@ColumnDefault("CURRENT_TIMESTAMP")
	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@ColumnDefault("CURRENT_TIMESTAMP")
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	@Column(name = "deleted_at")
	private LocalDateTime deletedAt;
}
