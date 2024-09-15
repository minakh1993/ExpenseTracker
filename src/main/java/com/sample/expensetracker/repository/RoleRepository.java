package com.sample.expensetracker.repository;

import com.sample.expensetracker.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author M.khoshnevisan
 * @since 9/15/2024
 */
@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {

    RoleEntity findByName(String name);
}