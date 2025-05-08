package com.prography.budgetbuddiesbackend.report.adapter.out.category;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
	@Query("SELECT c.name FROM CategoryEntity c WHERE c.user.id = :userId OR c.isDefault = true")
	Set<String> findAllCategoryNamesByUserIdOrDefault(@Param("userId") Long userId);
}
