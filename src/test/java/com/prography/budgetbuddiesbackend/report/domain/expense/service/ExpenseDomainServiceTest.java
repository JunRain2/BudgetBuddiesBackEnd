package com.prography.budgetbuddiesbackend.report.domain.expense.service;

import com.prography.budgetbuddiesbackend.report.domain.category.entity.Category;
import com.prography.budgetbuddiesbackend.report.domain.expense.entity.Expense;
import com.prography.budgetbuddiesbackend.report.domain.expense.exception.NotFoundExpenseException;
import com.prography.budgetbuddiesbackend.report.domain.expense.repository.ExpenseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

class ExpenseDomainServiceTest {
    @Mock ExpenseRepository expenseRepository;
    @InjectMocks
    ExpenseServiceImpl expenseDomainService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save_정상동작() {
        Expense expense = mock(Expense.class);
        given(expenseRepository.save(expense)).willReturn(expense);
        assertThat(expenseDomainService.save(expense)).isEqualTo(expense);
    }

    @Test
    void delete_정상동작() {
        Expense expense = mock(Expense.class);
        expenseDomainService.delete(expense);
        then(expenseRepository).should().delete(expense);
    }

    @Test
    void findById_정상동작() {
        Expense expense = mock(Expense.class);
        given(expenseRepository.findById(1L)).willReturn(Optional.of(expense));
        assertThat(expenseDomainService.findById(1L)).isEqualTo(expense);
    }

    @Test
    void findById_없으면_예외() {
        given(expenseRepository.findById(1L)).willReturn(Optional.empty());
        assertThatThrownBy(() -> expenseDomainService.findById(1L)).isInstanceOf(NotFoundExpenseException.class);
    }

    @Test
    void reassignCategory_정상동작() {
        Category deleted = mock(Category.class);
        Category uncategorized = mock(Category.class);
        expenseDomainService.reassignCategory(deleted, uncategorized);
        then(expenseRepository).should().clearCategoryReference(deleted, uncategorized);
    }
} 