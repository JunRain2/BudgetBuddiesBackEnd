package com.prography.budgetbuddiesbackend.report.domain.category.service;

import java.util.List;

import com.prography.budgetbuddiesbackend.report.domain.category.entity.Category;
import com.prography.budgetbuddiesbackend.report.domain.category.entity.CategoryId;
import com.prography.budgetbuddiesbackend.user.entity.UserId;

public interface CategoryService {
	/**
	 * 카테고리를 저장합니다.
	 * @param category 저장할 카테고리
	 * @return 저장된 카테고리
	 */
	Category save(Category category);

	/**
	 * 카테고리를 삭제합니다.
	 * @param category 삭제할 카테고리
	 */
	void delete(Category category);

	/**
	 * ID로 카테고리를 조회합니다.
	 * @param id 조회할 카테고리 ID
	 * @return 조회된 카테고리
	 * @throws NotFoundCategoryException 카테고리가 존재하지 않는 경우
	 */
	Category findById(CategoryId id);

	/**
	 * 사용자의 카테고리 목록을 조회합니다.
	 * @param userId 사용자 ID
	 * @return 카테고리 목록
	 */
	List<Category> findUserCategories(UserId userId);

	/**
	 * 미분류 카테고리를 조회합니다.
	 * @return 미분류 카테고리
	 * @throws NotFoundCategoryException 미분류 카테고리가 존재하지 않는 경우
	 */
	Category findUncategorizedCategory();
}
