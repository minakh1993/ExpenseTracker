package com.sample.expensetracker.converter;

import com.sample.expensetracker.api.CategoryDto;
import com.sample.expensetracker.entity.CategoryEntity;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author M.khoshnevisan
 * @since 9/13/2024
 */
@Component
public class CategoryConverter {

    public List<CategoryDto> convertCategories(List<CategoryEntity> allCategories) {
        List<CategoryDto> categoryList = new ArrayList<>();
        if (CollectionUtils.isEmpty(allCategories)) {
            return categoryList;
        }
        for (CategoryEntity category : allCategories) {
            categoryList.add(convertCategory(category));
        }
        return categoryList;
    }

    public CategoryDto convertCategory(CategoryEntity category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        return categoryDto;
    }

    public CategoryEntity convertToEntity(CategoryDto categoryDto) {
        CategoryEntity category = new CategoryEntity();
        category.setName(categoryDto.getName());
        return category;
    }
}