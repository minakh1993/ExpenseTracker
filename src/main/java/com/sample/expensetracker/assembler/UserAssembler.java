package com.sample.expensetracker.assembler;

import com.sample.expensetracker.api.LoginResponseDto;
import com.sample.expensetracker.api.UserInfoDto;
import com.sample.expensetracker.entity.UserEntity;
import org.springframework.stereotype.Component;

/**
 * @author M.khoshnevisan
 * @since 9/12/2024
 */
@Component
public class UserAssembler {

    public LoginResponseDto convertToLoginResponseDto(UserEntity user) {
        LoginResponseDto loginResponseDto = new LoginResponseDto();
        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.setName(user.getName());
        userInfoDto.setFamily(user.getFamily());
        userInfoDto.setNationalCode(user.getNationalCode());
        loginResponseDto.setUserInfoDto(userInfoDto);
        return loginResponseDto;
    }
}