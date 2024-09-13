package com.sample.expensetracker.service;

import com.sample.expensetracker.repository.ExpenseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author M.khoshnevisan
 * @since 9/13/2024
 */
@Service
@AllArgsConstructor
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
}