package com.sample.expensetracker.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sample.expensetracker.cache.KeyProvider;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.checkerframework.common.value.qual.MinLen;

/**
 * @author M.khoshnevisan
 * @since 9/13/2024
 */
public class CategoryDto implements KeyProvider {

    @Schema(description = "${CategoryDto.id}")
    private int id;

    @Schema(description = "${CategoryDto.name}", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "please provide category name")
    @Size(min = 2, max = 40)
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    @JsonIgnore
    public String getKey() {
        return String.valueOf(id);
    }
}