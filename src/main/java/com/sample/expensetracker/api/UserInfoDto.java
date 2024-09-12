package com.sample.expensetracker.api;

import lombok.Data;

/**
 * @author M.khoshnevisan
 * @since 9/12/2024
 */
@Data
public class UserInfoDto {
    private String name;
    private String family;
    private String nationalCode;
}