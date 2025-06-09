package com.prography.budgetbuddiesbackend.report.domain.category.service;

import com.prography.budgetbuddiesbackend.report.domain.category.entity.Category;
import com.prography.budgetbuddiesbackend.report.domain.category.exception.NotFoundCategoryException;
import com.prography.budgetbuddiesbackend.report.domain.category.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

class CategoryDomainServiceTest {
    @Mock CategoryRepository categoryRepository;
    @InjectMocks CategoryDomainService categoryDomainService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save_정상동작() {
        Category category = mock(Category.class);
        given(categoryRepository.save(category)).willReturn(category);
        assertThat(categoryDomainService.save(category)).isEqualTo(category);
    }

    @Test
    void delete_정상동작() {
        Category category = mock(Category.class);
        categoryDomainService.delete(category);
        then(categoryRepository).should().delete(category);
    }

    @Test
    void findById_정상동작() {
        Category category = mock(Category.class);
        given(categoryRepository.findById(1L)).willReturn(Optional.of(category));
        assertThat(categoryDomainService.findById(1L)).isEqualTo(category);
    }

    @Test
    void findById_없으면_예외() {
        given(categoryRepository.findById(1L)).willReturn(Optional.empty());
        assertThatThrownBy(() -> categoryDomainService.findById(1L)).isInstanceOf(NotFoundCategoryException.class);
    }
} 