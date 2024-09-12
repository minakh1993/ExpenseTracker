package com.sample.expensetracker.api;

import lombok.Data;

/**
 * @author M.khoshnevisan
 * @since 9/12/2024
 */
@Data
public class LoginResponseDto {
    private String jwtToken;
    private UserInfoDto userInfoDto;
}