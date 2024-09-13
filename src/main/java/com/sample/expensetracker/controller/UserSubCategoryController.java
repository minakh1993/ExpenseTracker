package com.sample.expensetracker.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author M.khoshnevisan
 * @since 9/13/2024
 */
@RestController
@RequestMapping("/subCategory")
@Slf4j
@AllArgsConstructor
@Tag(name = "${UserSubCategoryController.serviceNames}")
public class UserSubCategoryController {

}