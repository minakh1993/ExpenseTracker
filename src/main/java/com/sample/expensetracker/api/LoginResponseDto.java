package com.sample.expensetracker.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author M.khoshnevisan
 * @since 9/12/2024
 */
@Data
public class LoginResponseDto {
    @Schema(description = "${LoginRequestDto.jwtToken}")
    private String jwtToken;
    @Schema(description = "${LoginRequestDto.userInfoDto}")
    private UserInfoDto userInfoDto;
}