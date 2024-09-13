package com.sample.expensetracker.repository;

import com.sample.expensetracker.entity.ExpenseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author M.khoshnevisan
 * @since 9/13/2024
 */
@Repository
public interface ExpenseRepository extends JpaRepository<ExpenseEntity, Integer> {
}