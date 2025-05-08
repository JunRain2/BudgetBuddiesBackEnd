package com.prography.budgetbuddiesbackend.common.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@SQLDelete(sql = "UPDATE #{#entityName} SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public abstract class BaseEntity {

	@NotNull
	@ColumnDefault("CURRENT_TIMESTAMP")
	@CreatedDate
	@Column(name = "created_at", nullable = false, updatable = false)
	protected LocalDateTime createdAt;

	@ColumnDefault("CURRENT_TIMESTAMP")
	@LastModifiedDate
	@Column(name = "updated_at")
	protected LocalDateTime updatedAt;

	@Column(name = "deleted_at")
	protected LocalDateTime deletedAt;
}
