package com.sample.expensetracker.controller;

import com.sample.expensetracker.api.CategoryDto;
import com.sample.expensetracker.service.CategoryCacheableService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author M.khoshnevisan
 * @since 9/13/2024
 */
@RestController
@RequestMapping("/category")
@Slf4j
@AllArgsConstructor
public class CategoryController {

    private final CategoryCacheableService categoryService;

    @PostMapping("/add")
    @Secured("ROLE_ADMIN")
    public void addCategory(@RequestBody CategoryDto dto) {
        categoryService.saveCategory(dto);
    }

    @GetMapping("/all")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public List<CategoryDto> getAllCategories() {
        return categoryService.cacheableGetAll();
    }
}