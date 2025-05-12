package com.prography.budgetbuddiesbackend.report.domain.category.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.prography.budgetbuddiesbackend.report.domain.category.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
	@Query("SELECT c.name FROM Category c WHERE (c.user.id = :userId OR c.isDefault = true) AND c.deletedAt IS NULL")
	Set<String> findAllCategoryNamesByUserIdOrDefault(@Param("userId") Long userId);

	@Query("SELECT c FROM Category c WHERE (c.user.id = :userId OR c.isDefault = true) AND c.deletedAt IS NULL")
	List<Category> findUserCategoriesByUserId(@Param("userId") Long userId);
}
