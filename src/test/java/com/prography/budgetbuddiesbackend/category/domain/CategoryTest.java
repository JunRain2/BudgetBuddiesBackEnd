package com.prography.budgetbuddiesbackend.category.domain;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import org.junit.jupiter.api.Test;

class CategoryTest {
	@Test
	void 기본_카테고리_변경불가() {
		Category category = Category.of(-1L, -1L, "TEST", false);

		assertThat(category.canModify(2L)).isFalse();
	}

	@Test
	void 사용자가_일치하지_않아_변경불가() {
		Category category = Category.of(-1L, null, "TEST", true);

		assertThat(category.canModify(2L)).isFalse();
	}
}