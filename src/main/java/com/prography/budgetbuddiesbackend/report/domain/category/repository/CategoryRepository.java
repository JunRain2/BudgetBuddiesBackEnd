package com.prography.budgetbuddiesbackend.report.domain.category.repository;

import com.prography.budgetbuddiesbackend.report.domain.category.entity.Category;
import com.prography.budgetbuddiesbackend.report.domain.category.entity.CategoryId;
import com.prography.budgetbuddiesbackend.report.domain.category.entity.CategoryType;
import com.prography.budgetbuddiesbackend.user.entity.UserId;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category, CategoryId> {

  @Query("SELECT c.name FROM Category c WHERE (c.userId = :userId OR c.type = :type) AND c.deletedAt IS NULL")
  Set<String> findAllCategoryNamesByUserIdOrType(@Param("userId") UserId userId,
      @Param("type") CategoryType type);

  @Query("SELECT c FROM Category c WHERE (c.userId = :userId OR c.type = :type) AND c.deletedAt IS NULL")
  List<Category> findUserCategoriesByUserIdOrType(@Param("userId") UserId userId,
      @Param("type") CategoryType type);

  Optional<Category> findByName(@Size(max = 20) @NotNull String name);
}
