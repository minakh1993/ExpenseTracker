package com.sample.expensetracker.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.jdbc.lock.DefaultLockRepository;
import org.springframework.integration.jdbc.lock.JdbcLockRegistry;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @author M.khoshnevisan
 * @since 9/17/2024
 */
@Configuration
public class LockConfig {

    @Bean
    public JdbcLockRegistry jdbcLockRegistry(DefaultLockRepository lockRepository) {
        return new JdbcLockRegistry(lockRepository);
    }

    @Bean
    public DefaultLockRepository lockRepository(DataSource dataSource) {
        return new DefaultLockRepository(dataSource);
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}