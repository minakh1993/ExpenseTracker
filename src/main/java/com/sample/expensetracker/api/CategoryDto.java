package com.sample.expensetracker.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sample.expensetracker.cache.KeyProvider;

/**
 * @author M.khoshnevisan
 * @since 9/13/2024
 */
public class CategoryDto implements KeyProvider {

    private int id;

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