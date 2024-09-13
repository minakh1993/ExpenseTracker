package com.sample.expensetracker.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
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
@Tag(name = "${ExpenseController.serviceNames}")
public class ExpenseController {

    @GetMapping("/save")
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public void saveExpense() {
        log.info("save expense here!");
    }
}