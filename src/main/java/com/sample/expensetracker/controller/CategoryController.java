package com.sample.expensetracker.controller;

import com.sample.expensetracker.api.CategoryDto;
import com.sample.expensetracker.exception.DuplicateCategoryException;
import com.sample.expensetracker.service.CategoryCacheableService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
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

    /**
     * @param dto
     * @throws DuplicateCategoryException when category name is already created
     */
    @PostMapping(value = "/add",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Secured("ROLE_ADMIN")
    public CategoryDto addCategory(@RequestBody CategoryDto dto) throws DuplicateCategoryException {
        return categoryService.saveCategory(dto);
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public List<CategoryDto> getAllCategories() {
        return categoryService.cacheableGetAll();
    }

    @DeleteMapping("/remove/{categoryId}")
    @Secured("ROLE_ADMIN")
    public void removeCategory(@PathVariable("categoryId") Integer categoryId) {
        categoryService.removeCategory(categoryId);
    }
}