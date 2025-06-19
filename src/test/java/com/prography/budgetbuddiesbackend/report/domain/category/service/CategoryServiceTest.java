package com.prography.budgetbuddiesbackend.report.domain.category.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.prography.budgetbuddiesbackend.report.domain.category.entity.Category;
import com.prography.budgetbuddiesbackend.report.domain.category.entity.CategoryType;
import com.prography.budgetbuddiesbackend.report.domain.category.exception.DuplicateCategoryNameException;
import com.prography.budgetbuddiesbackend.report.domain.category.exception.NotFoundCategoryException;
import com.prography.budgetbuddiesbackend.report.domain.category.repository.CategoryRepository;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {
	@InjectMocks
	private CategoryServiceImpl categoryService;

	@Mock
	private CategoryRepository categoryRepository;

	private Long userId;
	private Category category;

	@BeforeEach
	void setUp() {
		userId = 1L;
		category = Category.of(userId, "식비");
	}

	@Test
	@DisplayName("카테고리를 저장한다")
	void save_정상동작() {
		// given
		when(categoryRepository.findAllCategoryNamesByUserIdOrType(userId, CategoryType.DEFAULT))
			.thenReturn(Set.of());
		when(categoryRepository.save(any(Category.class))).thenReturn(category);

		// when
		Category savedCategory = categoryService.save(category);

		// then
		assertThat(savedCategory).isEqualTo(category);
		verify(categoryRepository).save(category);
	}

	@Test
	@DisplayName("중복된 카테고리 이름으로 저장시 예외가 발생한다")
	void save_중복이름_예외() {
		// given
		when(categoryRepository.findAllCategoryNamesByUserIdOrType(userId, CategoryType.DEFAULT))
			.thenReturn(Set.of("식비"));

		// when & then
		assertThatThrownBy(() -> categoryService.save(category))
			.isInstanceOf(DuplicateCategoryNameException.class);
		verify(categoryRepository, never()).save(any(Category.class));
	}

	@Test
	@DisplayName("카테고리를 삭제한다")
	void delete_정상동작() {
		// given
		doNothing().when(categoryRepository).delete(any(Category.class));

		// when
		categoryService.delete(category);

		// then
		verify(categoryRepository).delete(category);
	}

	@Test
	@DisplayName("ID로 카테고리를 조회한다")
	void findById_정상동작() {
		// given
		when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

		// when
		Category foundCategory = categoryService.findById(1L);

		// then
		assertThat(foundCategory).isEqualTo(category);
		verify(categoryRepository).findById(1L);
	}

	@Test
	@DisplayName("존재하지 않는 ID로 조회시 예외가 발생한다")
	void findById_없으면_예외() {
		// given
		when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

		// when & then
		assertThatThrownBy(() -> categoryService.findById(1L))
			.isInstanceOf(NotFoundCategoryException.class);
		verify(categoryRepository).findById(1L);
	}

	@Test
	@DisplayName("사용자의 카테고리 목록을 조회한다")
	void findUserCategories_정상동작() {
		// given
		List<Category> categories = List.of(category);
		when(categoryRepository.findUserCategoriesByUserIdOrType(userId, CategoryType.DEFAULT))
			.thenReturn(categories);

		// when
		List<Category> foundCategories = categoryService.findUserCategories(userId);

		// then
		assertThat(foundCategories).isEqualTo(categories);
		verify(categoryRepository).findUserCategoriesByUserIdOrType(userId, CategoryType.DEFAULT);
	}

	@Test
	@DisplayName("미분류 카테고리를 조회한다")
	void findUncategorizedCategory_정상동작() {
		// given
		Category uncategorized = Category.of(userId, "카테고리 없음");
		when(categoryRepository.findByName("카테고리 없음")).thenReturn(Optional.of(uncategorized));

		// when
		Category foundCategory = categoryService.findUncategorizedCategory();

		// then
		assertThat(foundCategory).isEqualTo(uncategorized);
		verify(categoryRepository).findByName("카테고리 없음");
	}

	@Test
	@DisplayName("미분류 카테고리가 없으면 예외가 발생한다")
	void findUncategorizedCategory_없으면_예외() {
		// given
		when(categoryRepository.findByName("카테고리 없음")).thenReturn(Optional.empty());

		// when & then
		assertThatThrownBy(() -> categoryService.findUncategorizedCategory())
			.isInstanceOf(NotFoundCategoryException.class);
		verify(categoryRepository).findByName("카테고리 없음");
	}
} 