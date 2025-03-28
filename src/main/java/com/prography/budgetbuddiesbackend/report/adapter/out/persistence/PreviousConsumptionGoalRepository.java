package com.prography.budgetbuddiesbackend.report.adapter.out.persistence;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

interface PreviousConsumptionGoalRepository extends JpaRepository<PreviousConsumptionGoalEntity, Long> {
	List<PreviousConsumptionGoalEntity> findByGoalAtAndUserId(LocalDate goalAt, Long userId);
}
