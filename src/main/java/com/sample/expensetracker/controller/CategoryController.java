package com.sample.expensetracker.controller;

import com.sample.expensetracker.api.CategoryDto;
import com.sample.expensetracker.exception.DuplicateCategoryException;
import com.sample.expensetracker.service.CategoryCacheableService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
@Tag(name = "${CategoryController.serviceNames}")
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
    @Operation(operationId = "addCategory", description = "${addCategory}",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok"),
                    @ApiResponse(responseCode = "401", description = "unauthorized"),
                    @ApiResponse(responseCode = "400", description = "md:addCategory.md"),
            })
    public CategoryDto addCategory(@RequestBody @Valid CategoryDto dto) throws DuplicateCategoryException {
        return categoryService.saveCategory(dto);
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @Operation(operationId = "getAllCategories", description = "${getAllCategories}",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok"),
                    @ApiResponse(responseCode = "401", description = "unauthorized")
            })
    public List<CategoryDto> getAllCategories() {
        return categoryService.cacheableGetAll();
    }

    @DeleteMapping(value = "/remove/{categoryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Secured("ROLE_ADMIN")
    @Operation(operationId = "removeCategory", description = "${removeCategory}",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok"),
                    @ApiResponse(responseCode = "401", description = "unauthorized")
            })

    public void removeCategory(@Schema(description = "${CategoryDto.id}")
                               @PathVariable("categoryId") Integer categoryId) {
        categoryService.removeCategory(categoryId);
    }
}