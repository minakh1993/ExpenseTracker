package com.sample.expensetracker.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author M.khoshnevisan
 * @since 9/12/2024
 */
@RestController
@RequestMapping("/expense")
@Slf4j
public class ExpenseController {

    @GetMapping("/save")
    public void saveExpense() {
        log.info("save expense here!");
    }
}