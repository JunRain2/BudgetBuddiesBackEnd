package com.prography.budgetbuddiesbackend.report.domain.category.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.prography.budgetbuddiesbackend.common.RepositoryTest;
import com.prography.budgetbuddiesbackend.report.domain.category.entity.Category;
import com.prography.budgetbuddiesbackend.report.domain.category.entity.CategoryType;

@RepositoryTest
class CategoryRepositoryTest {
	@Autowired
	private CategoryRepository categoryRepository;

	@Test
	void 사용자의_카테고리_이름_조회시_해당_사용자_카테고리만_반환된다() {
		// given
		Long userId1 = 1L;
		Long userId2 = 2L;

		final String user1CategoryName = "USER1_CATEGORY_NAME";
		final String user2CategoryName = "USER2_CATEGORY_NAME";

		Category user1Category = Category.of(userId1, user1CategoryName);
		Category user2Category = Category.of(userId2, user2CategoryName);
		categoryRepository.saveAll(List.of(user1Category, user2Category));

		// when
		Set<String> result = categoryRepository.findAllCategoryNamesByUserIdOrType(userId1, CategoryType.DEFAULT);

		// then
		assertThat(result).contains(user1CategoryName).doesNotContain(user2CategoryName);
	}

	@Test
	void 사용자가_디폴트_카테고리만_가진_경우_디폴트_카테고리_이름만_반환된다() {
		// given
		Long userId = 1L;

		int defaultCategoryCount = categoryRepository.findUserCategoriesByUserIdOrType(null, CategoryType.DEFAULT)
			.size();

		// when
		Set<String> result = categoryRepository.findAllCategoryNamesByUserIdOrType(userId, CategoryType.DEFAULT);

		// then
		assertThat(result).hasSize(defaultCategoryCount);
	}

	@Test
	void 존재하지_않는_유저의_카테고리_이름_조회시_빈_결과_반환() {
		// given
		Long 없는유저ID = -1L;
		int defaultCategoryCount = categoryRepository.findUserCategoriesByUserIdOrType(null, CategoryType.DEFAULT)
			.size();
		// when
		Set<String> result = categoryRepository.findAllCategoryNamesByUserIdOrType(없는유저ID, CategoryType.DEFAULT);
		// then
		assertThat(result).hasSize(defaultCategoryCount);
	}

	@Test
	void 삭제된_카테고리는_카테고리_이름_조회시_포함되지_않음() {
		// given
		Long userId = 1L;
		Category category = categoryRepository.save(Category.of(userId, "삭제될카테고리"));
		categoryRepository.delete(category);

		// when
		Set<String> result = categoryRepository.findAllCategoryNamesByUserIdOrType(userId, CategoryType.DEFAULT);

		// then
		assertThat(result).isNotEmpty().doesNotContain("삭제될카테고리");
	}

	@Test
	void 카테고리_이름이_최대길이_20자일_때_정상_저장_및_조회된다() {
		// given
		Long userId = 1L;
		String name = "가".repeat(20);
		categoryRepository.save(Category.of(userId, name));
		// when
		Set<String> result = categoryRepository.findAllCategoryNamesByUserIdOrType(userId, CategoryType.DEFAULT);
		// then
		assertThat(result).contains(name);
	}

	@Test
	void 카테고리_이름이_1자일_때_정상_저장_및_조회된다() {
		// given
		Long userId = 1L;
		String name = "가";
		categoryRepository.save(Category.of(userId, name));
		// when
		Set<String> result = categoryRepository.findAllCategoryNamesByUserIdOrType(userId, CategoryType.DEFAULT);
		// then
		assertThat(result).contains(name);
	}

	@Test
	void 카테고리_이름이_빈문자열일_때_저장시_예외가_발생한다() {
		// given
		Long userId = 1L;
		String name = "";
		// when & then
		assertThatThrownBy(() -> categoryRepository.save(Category.of(userId, name))).isInstanceOf(Exception.class);
	}

	@Test
	void 카테고리_이름이_21자일_때_저장시_예외가_발생한다() {
		// given
		Long userId = 1L;
		String name = "가".repeat(21);
		// when & then
		assertThatThrownBy(() -> categoryRepository.save(Category.of(userId, name))).isInstanceOf(Exception.class);
	}

	@Test
	void 사용자의_카테고리_엔티티_정상_조회() {
		// given
		Long userId = 1L;
		String name = "카테고리1";
		categoryRepository.save(Category.of(userId, name));
		// when
		List<Category> result = categoryRepository.findUserCategoriesByUserIdOrType(userId, CategoryType.DEFAULT);
		// then
		assertThat(result.stream().map(Category::getName)).contains(name);
	}

	@Test
	void 존재하지_않는_유저의_카테고리_엔티티_조회시_빈_결과_반환() {
		Long 없는유저ID = -1L;
		int defaultCategoryCount = categoryRepository.findUserCategoriesByUserIdOrType(null, CategoryType.DEFAULT)
			.size();
		List<Category> result = categoryRepository.findUserCategoriesByUserIdOrType(없는유저ID, CategoryType.DEFAULT);
		assertThat(result).hasSize(defaultCategoryCount);
	}
}