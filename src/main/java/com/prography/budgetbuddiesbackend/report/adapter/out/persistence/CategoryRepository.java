package com.prography.budgetbuddiesbackend.report.adapter.out.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
	@Query("SELECT a FROM CategoryEntity a WHERE a.user = :user OR a.isDefault = true")
	List<CategoryEntity> findByUserOrIsDefault(@Param("user") UserEntity user);

	Optional<CategoryEntity> findByIdAndUser(Long id, UserEntity user);
}
