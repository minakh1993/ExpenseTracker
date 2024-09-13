package com.sample.expensetracker.entity;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

/**
 * @author M.khoshnevisan
 * @since 9/12/2024
 */
@Table(name = "APP_ROLE")
@Entity
public class RoleEntity implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "NAME", nullable = false)
    private String name;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return name;
    }
}