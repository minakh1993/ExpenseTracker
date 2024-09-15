package com.sample.expensetracker.api;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * @author M.khoshnevisan
 * @since 9/12/2024
 */
@Data
public class LoginRequestDto {
    @Schema(description = "${LoginRequestDto.username}", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "Please provide a username")
    private String username;
    @Schema(description = "${LoginRequestDto.password}", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "Please provide a password")
    private String password;
}