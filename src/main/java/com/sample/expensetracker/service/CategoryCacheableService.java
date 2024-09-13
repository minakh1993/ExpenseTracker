package com.sample.expensetracker.service;

import com.sample.expensetracker.api.CategoryDto;
import com.sample.expensetracker.cache.AbstractCacheableService;
import com.sample.expensetracker.cache.CacheName;
import com.sample.expensetracker.converter.CategoryConverter;
import com.sample.expensetracker.entity.CategoryEntity;
import com.sample.expensetracker.exception.DuplicateCategoryException;
import com.sample.expensetracker.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author M.khoshnevisan
 * @since 9/13/2024
 */
@Service
@AllArgsConstructor
public class CategoryCacheableService extends AbstractCacheableService<CategoryDto> {

    private final CategoryRepository categoryRepository;
    private final CategoryConverter categoryConverter;

    @Override
    protected CacheName.CacheIdentifier getCacheName() {
        return CacheName.CacheIdentifier.CATEGORY_INFO;
    }

    @Override
    protected Class<CategoryDto> getType() {
        return CategoryDto.class;
    }

    @Override
    protected List<CategoryDto> getAllFromDataSource() {
        List<CategoryEntity> allCategories = categoryRepository.findAll();
        return categoryConverter.convertCategories(allCategories);
    }

    public synchronized CategoryDto saveCategory(CategoryDto dto) throws DuplicateCategoryException {
        List<CategoryDto> categoryList = cacheableGetAll();
        for (CategoryDto categoryDto : categoryList) {
            if (categoryDto.getName().equalsIgnoreCase(dto.getName())) {
                throw new DuplicateCategoryException("duplicate category name : " + dto.getName());
            }
        }
        CategoryEntity category = categoryConverter.convertToEntity(dto);
        CategoryEntity savedCategory = categoryRepository.save(category);
        CategoryDto categoryDto = categoryConverter.convertCategory(savedCategory);
        addToCache(categoryDto);
        return categoryDto;
    }

    public void removeCategory(Integer categoryId) {
        CategoryEntity entity = new CategoryEntity();
        entity.setId(categoryId);
        categoryRepository.delete(entity);
        removeFromCache(String.valueOf(categoryId));
    }
}