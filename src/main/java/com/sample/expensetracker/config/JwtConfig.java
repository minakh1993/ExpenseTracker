package com.sample.expensetracker.config;

import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.time.Duration;

/**
 * @author M.khoshnevisan
 * @since 9/12/2024
 */
@Component
@ConfigurationProperties("jwt")
@Getter
@Setter
@Validated
public class JwtConfig {
    @NotNull
    private String issuer;
    @NotNull
    private Duration expiresIn;
}