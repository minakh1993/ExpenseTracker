package com.sample.expensetracker.converter;

import com.sample.expensetracker.api.LoginResponseDto;
import com.sample.expensetracker.api.SignUpRequestDto;
import com.sample.expensetracker.api.SignUpResponseDto;
import com.sample.expensetracker.api.UserInfoDto;
import com.sample.expensetracker.entity.RoleEntity;
import com.sample.expensetracker.entity.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * @author M.khoshnevisan
 * @since 9/12/2024
 */
@Component
@AllArgsConstructor
public class UserConverter {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public LoginResponseDto convertToLoginResponseDto(UserEntity user, String jwt) {
        LoginResponseDto loginResponseDto = new LoginResponseDto();
        loginResponseDto.setJwtToken(jwt);
        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.setName(user.getName());
        userInfoDto.setFamily(user.getFamily());
        userInfoDto.setNationalCode(user.getNationalCode());
        loginResponseDto.setUserInfoDto(userInfoDto);
        return loginResponseDto;
    }

    public UserEntity convertToUserEntity(SignUpRequestDto requestDto, RoleEntity role) {
        UserEntity user = new UserEntity();
        user.setNationalCode(requestDto.getNationalCode());
        user.setUsername(requestDto.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(requestDto.getPassword()));
        user.setEnabled(true);
        user.setName(requestDto.getName());
        user.setFamily(requestDto.getFamily());
        ArrayList<RoleEntity> roles = new ArrayList<>() {{
            add(role);
        }};
        user.setRoles(roles);
        return user;
    }

    public SignUpResponseDto convertToSignUpResponseDto(UserEntity savedEntity) {
        SignUpResponseDto signUpResponseDto = new SignUpResponseDto();
        signUpResponseDto.setId(savedEntity.getId());
        return signUpResponseDto;
    }
}