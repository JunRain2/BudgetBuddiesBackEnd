package com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.service;

import com.prography.budgetbuddiesbackend.report.domain.category.entity.Category;
import com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.entity.ConsumptionGoal;
import com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.repository.ConsumptionGoalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

class ConsumptionGoalDomainServiceTest {
    @Mock ConsumptionGoalRepository consumptionGoalRepository;
    @InjectMocks ConsumptionGoalDomainService consumptionGoalDomainService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save_정상동작() {
        ConsumptionGoal goal = mock(ConsumptionGoal.class);
        given(consumptionGoalRepository.save(goal)).willReturn(goal);
        assertThat(consumptionGoalDomainService.save(goal)).isEqualTo(goal);
    }

    @Test
    void deleteAllByCategory_정상동작() {
        Category category = mock(Category.class);
        List<ConsumptionGoal> goals = List.of(mock(ConsumptionGoal.class));
        given(consumptionGoalRepository.findByCategory(category)).willReturn(goals);
        consumptionGoalDomainService.deleteAllByCategory(category);
        then(consumptionGoalRepository).should().deleteAll(goals);
    }
} 