package com.sample.expensetracker.entity;

import jakarta.persistence.*;

/**
 * @author M.khoshnevisan
 * @since 9/13/2024
 */
@Entity
@Table(name = "USER_SUB_CATEGORY", uniqueConstraints = @UniqueConstraint(name = "UK_SUBCATEGORY_1", columnNames = {
        "USER_ID", "CATEGORY_ID", "SUB_CATEGORY"}))
public class UserSubCategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", foreignKey = @ForeignKey(name = "FK_USER"), nullable = false, updatable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY_ID", foreignKey = @ForeignKey(name = "FK_CATEGORY"), nullable = false, updatable = false)
    private CategoryEntity category;

    @Column(name = "SUB_CATEGORY", nullable = false)
    private String subCategoryName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }
}