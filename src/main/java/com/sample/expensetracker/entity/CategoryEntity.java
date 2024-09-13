package com.sample.expensetracker.entity;

import jakarta.persistence.*;

/**
 * @author M.khoshnevisan
 * @since 9/13/2024
 */
@Table(name = "CATEGORY", uniqueConstraints = @UniqueConstraint(name = "UK_CATEGORY_1", columnNames = {"NAME"}))
@Entity
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private int id;

    @Column(name = "NAME", nullable = false)
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
}