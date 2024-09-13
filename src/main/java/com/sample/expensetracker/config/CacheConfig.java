package com.sample.expensetracker.config;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

/**
 * @author M.khoshnevisan
 * @since 9/13/2024
 */
@Configuration
@ConfigurationProperties("cache")
@Data
@Validated
public class CacheConfig {
    @NotNull
    private Long categoryTimeToLive;
    @NotNull
    private Long userTimeToLive;
}