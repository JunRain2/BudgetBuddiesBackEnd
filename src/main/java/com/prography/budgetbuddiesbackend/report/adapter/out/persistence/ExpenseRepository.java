package com.prography.budgetbuddiesbackend.report.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

interface ExpenseRepository extends JpaRepository<ExpenseEntity, Long> {
}
