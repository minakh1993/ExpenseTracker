package com.sample.expensetracker.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

/**
 * @author M.khoshnevisan
 * @since 9/13/2024
 */
@Table(name = "EXPENSE")
@Entity
public class ExpenseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "AMOUNT", nullable = false)
    private BigDecimal amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SUB_CAT_ID", foreignKey = @ForeignKey(name = "FK_SUB_CAT"), nullable = false, updatable = false)
    private UserSubCategoryEntity userSubCategory;

    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public UserSubCategoryEntity getUserSubCategory() {
        return userSubCategory;
    }

    public void setUserSubCategory(UserSubCategoryEntity userSubCategory) {
        this.userSubCategory = userSubCategory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String desription) {
        this.description = desription;
    }
}