package com.prography.budgetbuddiesbackend.report.domain.category.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.prography.budgetbuddiesbackend.report.domain.category.entity.Category;
import com.prography.budgetbuddiesbackend.report.domain.category.entity.CategoryType;

public interface CategoryRepository extends JpaRepository<Category, Long> {
	@Query("SELECT c.name FROM Category c WHERE (c.user.id = :userId OR c.type = :type) AND c.deletedAt IS NULL")
	Set<String> findAllCategoryNamesByUserIdOrType(@Param("userId") Long userId, @Param("type") CategoryType type);

	@Query("SELECT c FROM Category c WHERE (c.user.id = :userId OR c.type = :type) AND c.deletedAt IS NULL")
	List<Category> findUserCategoriesByUserIdOrType(@Param("userId") Long userId, @Param("type") CategoryType type);
}
