package com.prography.budgetbuddiesbackend.report.domain.category.service;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.prography.budgetbuddiesbackend.common.ServiceIntegrationTest;
import com.prography.budgetbuddiesbackend.report.domain.category.dto.request.RegisterCategoryRequest;
import com.prography.budgetbuddiesbackend.report.domain.category.dto.response.UserCategoryResponse;
import com.prography.budgetbuddiesbackend.report.domain.category.entity.Category;
import com.prography.budgetbuddiesbackend.report.domain.category.entity.CategoryType;
import com.prography.budgetbuddiesbackend.report.domain.category.exception.DuplicateCategoryNameException;
import com.prography.budgetbuddiesbackend.report.domain.category.exception.UnmodifiableCategoryException;
import com.prography.budgetbuddiesbackend.report.domain.category.repository.CategoryRepository;
import com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.repository.ConsumptionGoalRepository;
import com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.service.ConsumptionGoalDomainService;
import com.prography.budgetbuddiesbackend.report.domain.expense.entity.Expense;
import com.prography.budgetbuddiesbackend.report.domain.expense.repository.ExpenseRepository;
import com.prography.budgetbuddiesbackend.report.domain.expense.service.ExpenseDomainService;
import com.prography.budgetbuddiesbackend.report.domain.user.entity.User;
import com.prography.budgetbuddiesbackend.report.domain.user.repository.UserRepository;

@ServiceIntegrationTest
class CategoryServiceIntegrationTest {
	@Autowired
	CategoryService categoryService;
	@Autowired
	CategoryDomainService categoryDomainService;
	@Autowired
	ExpenseDomainService expenseDomainService;
	@Autowired
	ConsumptionGoalDomainService consumptionGoalDomainService;
	@Autowired
	UserRepository userRepository;
	@Autowired
	CategoryRepository categoryRepository;
	@Autowired
	ConsumptionGoalRepository consumptionGoalRepository;
	@Autowired
	ExpenseRepository expenseRepository;

	@Test
	void 카테고리_정상_생성_및_소비목표_자동생성() {
		// given
		User user = userRepository.save(User.of());
		RegisterCategoryRequest req = new RegisterCategoryRequest("식비");
		// when
		categoryService.registerCategory(req, user.getId());
		List<UserCategoryResponse> categories = categoryService.findUserCategories(user.getId());
		// then
		UserCategoryResponse 식비카테고리 = categories.stream()
			.filter(c -> c.name().equals("식비"))
			.findFirst()
			.orElseThrow();
		Category categoryEntity = categoryRepository.findById(식비카테고리.categoryId()).orElseThrow();
		assertThat(식비카테고리.name()).isEqualTo("식비");
		assertThat(consumptionGoalRepository.findByCategory(categoryEntity)
			.stream()
			.anyMatch(g -> g.getCap() == 200000)).isTrue();
	}

	@Test
	void 동일_이름_중복_생성시_예외() {
		// given
		User user = userRepository.save(User.of());
		RegisterCategoryRequest req = new RegisterCategoryRequest("교통");
		categoryService.registerCategory(req, user.getId());
		// when & then
		assertThatThrownBy(() -> categoryService.registerCategory(req, user.getId())).isInstanceOf(
			DuplicateCategoryNameException.class);
	}

	@Test
	void 기본_카테고리_삭제시_예외() {
		// given
		User user = userRepository.save(User.of());
		Category 기본카테고리 = categoryRepository.findUserCategoriesByUserIdOrType(null, CategoryType.DEFAULT).get(0);
		// when & then
		assertThatThrownBy(() -> categoryService.deleteCategory(기본카테고리.getId(), user.getId())).isInstanceOf(
			UnmodifiableCategoryException.class);
	}

	@Test
	void 커스텀_카테고리_삭제시_소비내역_uncategorized_이동_및_목표_삭제() {
		// given
		User user = userRepository.save(User.of());
		RegisterCategoryRequest req = new RegisterCategoryRequest("여행");
		categoryService.registerCategory(req, user.getId());
		Category category = categoryRepository.findAll()
			.stream()
			.filter(c -> "여행".equals(c.getName()))
			.findFirst()
			.get();
		Expense expense = expenseRepository.save(Expense.of(user, category, 10000, "여행비", LocalDate.now()));
		// when
		categoryService.deleteCategory(category.getId(), user.getId());
		// then
		Expense updated = expenseRepository.findById(expense.getId()).get();
		assertThat(updated.getCategory().getId()).isEqualTo(1L);
		assertThat(consumptionGoalRepository.findByCategory(category)).isEmpty();
	}
} 