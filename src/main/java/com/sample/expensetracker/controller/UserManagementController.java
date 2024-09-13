package com.sample.expensetracker.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author M.khoshnevisan
 * @since 9/12/2024
 */
@RestController
@RequestMapping("/management/user")
@Slf4j
@Tag(name = "${UserManagementController.serviceNames}")
public class UserManagementController {

    @PostMapping("/save")
    @Secured("ROLE_ADMIN")
    public void saveUser() {
      log.info("to save user");
    }
}