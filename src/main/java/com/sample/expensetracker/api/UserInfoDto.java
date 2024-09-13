package com.sample.expensetracker.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author M.khoshnevisan
 * @since 9/12/2024
 */
@Data
public class UserInfoDto {
    @Schema(description = "${UserInfoDto.name}")
    private String name;
    @Schema(description = "${UserInfoDto.family}")
    private String family;
    @Schema(description = "${UserInfoDto.nationalCode}")
    private String nationalCode;
}