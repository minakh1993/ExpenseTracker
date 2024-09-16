package com.sample.expensetracker.integration;

import com.sample.expensetracker.api.CategoryDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.hibernate.validator.internal.util.Contracts.assertTrue;

/**
 * @author M.khoshnevisan
 * @since 9/16/2024
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class CategoryITest extends BaseITest {

    public static final String ADD_CATEGORY_URL = "/category/add";
    public static final String GET_CATEGORIES_URL = "/category/all";
    public static final String REMOVE_CATEGORY_URL = "/category/remove";

    @BeforeEach
    public void setup() {
        restTemplate.getRestTemplate().setErrorHandler(new DefaultResponseErrorHandler());
        login();
    }

    @Test
    public void addCategory_withAddCategoryService_retrievedViaGetAllCategories() {
        CategoryDto dto = new CategoryDto();
        String addedCategoryName = "testCategory";
        dto.setName(addedCategoryName);
        HttpEntity<CategoryDto> request = new HttpEntity<>(dto, headers);
        ResponseEntity<CategoryDto> responseEntity = restTemplate
                .postForEntity(ADD_CATEGORY_URL, request, CategoryDto.class);
        assertNotNull(responseEntity.getBody().getId());

        ResponseEntity<List<CategoryDto>> allCategoriesEntity = restTemplate.exchange(GET_CATEGORIES_URL, HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<>() {
                });
        assertNotNull(allCategoriesEntity.getBody());
        assertTrue(allCategoriesEntity.getBody().stream().anyMatch(category ->
                category.getName().equals(addedCategoryName)), "new category");
    }
}