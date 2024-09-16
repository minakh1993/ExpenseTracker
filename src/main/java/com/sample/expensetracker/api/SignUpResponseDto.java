package com.sample.expensetracker.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author M.khoshnevisan
 * @since 9/15/2024
 */
@Data
public class SignUpResponseDto {

    @Schema(description = "${SignUpResponseDto.id}")
    private int id;
}