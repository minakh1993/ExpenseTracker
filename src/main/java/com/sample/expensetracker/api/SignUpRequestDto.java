package com.sample.expensetracker.api;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * @author M.khoshnevisan
 * @since 9/15/2024
 */
@Data
public class SignUpRequestDto {

    @NotEmpty
    @Size(min = 5, max = 20)
    @Schema(description = "${SignUpRequestDto.username}", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;

    @NotEmpty
    @Size(min = 5, max = 20)
    @Schema(description = "${SignUpRequestDto.password}", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;

    @NotEmpty
    @Size(min = 10, max = 10)
    @Schema(description = "${SignUpRequestDto.nationalCode}", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nationalCode;

    @NotEmpty
    @Size(min = 2, max = 50)
    @Schema(description = "${SignUpRequestDto.name}", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @NotEmpty
    @Size(min = 2, max = 50)
    @Schema(description = "${SignUpRequestDto.family}", requiredMode = Schema.RequiredMode.REQUIRED)
    private String family;
}