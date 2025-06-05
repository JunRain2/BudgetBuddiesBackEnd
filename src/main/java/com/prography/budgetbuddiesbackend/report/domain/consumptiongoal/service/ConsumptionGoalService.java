package com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.service;

import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prography.budgetbuddiesbackend.report.domain.category.entity.Category;
import com.prography.budgetbuddiesbackend.report.domain.category.service.CategoryService;
import com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.entity.ConsumptionGoal;
import com.prography.budgetbuddiesbackend.report.domain.consumptiongoal.repository.ConsumptionGoalRepository;
import com.prography.budgetbuddiesbackend.report.domain.user.entity.User;
import com.prography.budgetbuddiesbackend.report.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ConsumptionGoalService {
	private final ConsumptionGoalRepository consumptionGoalRepository;

	private final UserService userService;
	private final CategoryService categoryService;

	/**
	 * 사용자 기준, 카테고리별 월 단위 소비목표 조회
	 */
	public List<ConsumptionGoal> findGoalsByUserAndMonth(Long userId, YearMonth yearMonth) {
		return consumptionGoalRepository.findAll().stream()
			.filter(goal -> goal.getUser().getId().equals(userId) && goal.getGoalMonth().equals(yearMonth))
			.toList();
	}

	/**
	 * 복수 목표(cap) 일괄 수정
	 * @param userId 사용자
	 * @param yearMonth 연월
	 * @param categoryIdToCapMap categoryId -> cap
	 */
	public void updateGoalsInBatch(Long userId, YearMonth yearMonth, Map<Long, Integer> categoryIdToCapMap) {
		for (Map.Entry<Long, Integer> entry : categoryIdToCapMap.entrySet()) {
			Long categoryId = entry.getKey();
			Integer cap = entry.getValue();
			Optional<ConsumptionGoal> optionalGoal = consumptionGoalRepository.findAll().stream()
				.filter(goal -> goal.getUser().getId().equals(userId)
					&& goal.getCategory().getId().equals(categoryId)
					&& goal.getGoalMonth().equals(yearMonth))
				.findFirst();
			if (optionalGoal.isPresent()) {
				ConsumptionGoal goal = optionalGoal.get();
				goal.setCap(cap);
			} else {
				// 없으면 새로 생성 (고유성 보장)
				User user = userService.findById(userId);
				Category category = categoryService.findById(categoryId);
				ConsumptionGoal newGoal = ConsumptionGoal.of(user, category, cap, yearMonth);
				consumptionGoalRepository.save(newGoal);
			}
		}
	}

	public void deleteAllByCategory(Category category) {
		List<ConsumptionGoal> consumptionGoals = consumptionGoalRepository.findByCategory(category);
		consumptionGoalRepository.deleteAll(consumptionGoals);
	}

	public ConsumptionGoal save(ConsumptionGoal newConsumptionGoal) {
		return consumptionGoalRepository.save(newConsumptionGoal);
	}
}