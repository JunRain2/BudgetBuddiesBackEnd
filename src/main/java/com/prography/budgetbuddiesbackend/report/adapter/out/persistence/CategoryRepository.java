package com.prography.budgetbuddiesbackend.report.adapter.out.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
	@Query("SELECT a FROM CategoryEntity a WHERE a.userId = :userId OR a.isDefault = true")
	List<CategoryEntity> findByUserIdOrIsDefault(@Param("userId") Long userId);

	Optional<CategoryEntity> findByIdAndUserId(Long id, Long userId);

	List<CategoryEntity> findAllByUserId(Long userId);
}
