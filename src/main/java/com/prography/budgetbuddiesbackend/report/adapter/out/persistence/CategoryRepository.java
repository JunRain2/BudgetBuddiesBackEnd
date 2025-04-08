package com.prography.budgetbuddiesbackend.report.adapter.out.persistence;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
	Optional<CategoryEntity> findByUserIdAndId(Long userId, Long categoryId);

	List<CategoryEntity> findAllByUserIdOrIsDefaultTrue(Long userId);

	@Query("SELECT c.name FROM CategoryEntity c WHERE c.isDefault = true OR c.userId = :userId")
	Set<String> findNameByUserIdOrIsDefaultTrue(@Param("userId") Long userId);

}
