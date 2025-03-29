package com.prography.budgetbuddiesbackend.report.adapter.out.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
	Optional<CategoryEntity> findByUserIdAndId(Long userId, Long categoryId);

	List<CategoryEntity> findAllByUserIdAndIsDefaultTrue(Long userId);
}
