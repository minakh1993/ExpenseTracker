package com.sample.expensetracker.converter;

import com.sample.expensetracker.api.LoginResponseDto;
import com.sample.expensetracker.api.SignUpRequestDto;
import com.sample.expensetracker.api.SignUpResponseDto;
import com.sample.expensetracker.api.UserInfoDto;
import com.sample.expensetracker.entity.RoleEntity;
import com.sample.expensetracker.entity.UserEntity;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author M.khoshnevisan
 * @since 9/16/2024
 */
@ExtendWith(MockitoExtension.class)
public class UserConverterUTest {

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserConverter userConverter;

    private static final String TEST_JWT = "45reiorueoir";

    @BeforeEach
    public void setup() {
        userConverter = new UserConverter(bCryptPasswordEncoder);
    }

    @Test
    public void testConvertToLoginResponseDto_withJwtInput_convertJwtCorrectly() {
        LoginResponseDto loginResponseDto = userConverter.convertToLoginResponseDto(mock(UserEntity.class), TEST_JWT);
        assertEquals(TEST_JWT, loginResponseDto.getJwtToken());
    }

    @Test
    public void testConvertToLoginResponseDto_withUserInfo_convertUserInfoCorrectly() {
        UserEntity user = Instancio.create(UserEntity.class);
        LoginResponseDto loginResponseDto = userConverter.convertToLoginResponseDto(user, TEST_JWT);
        UserInfoDto userInfoDto = loginResponseDto.getUserInfoDto();
        assertNotNull(userInfoDto);
        assertEquals(user.getName(), userInfoDto.getName());
        assertEquals(user.getFamily(), userInfoDto.getFamily());
        assertEquals(user.getNationalCode(), userInfoDto.getNationalCode());
    }

    @Test
    public void testConvertToUserEntity_withSignUpData_convertToUserInfoCorrectly() {
        SignUpRequestDto signUpRequestDto = Instancio.create(SignUpRequestDto.class);
        UserEntity user = userConverter.convertToUserEntity(signUpRequestDto, mock(RoleEntity.class));
        assertEquals(signUpRequestDto.getUsername(), user.getUsername());
        assertEquals(signUpRequestDto.getName(), user.getName());
        assertEquals(signUpRequestDto.getNationalCode(), user.getNationalCode());
        assertEquals(signUpRequestDto.getFamily(), user.getFamily());
        assertTrue(user.isEnabled());
    }

    @Test
    public void testConvertToUserEntity_withPassword_convertToPassWordHashCorrectly() {
        SignUpRequestDto requestDto = mock(SignUpRequestDto.class);
        String testPass = "testPass";
        when(requestDto.getPassword()).thenReturn(testPass);
        String hashedPass = "i958345739dfksjkf";
        when(bCryptPasswordEncoder.encode(eq(testPass))).thenReturn(hashedPass);
        UserEntity user = userConverter.convertToUserEntity(requestDto, mock(RoleEntity.class));
        assertEquals(hashedPass, user.getPassword());
    }

    @Test
    public void testConvertToUserEntity_withRole_convertRoleCorrectly() {
        RoleEntity role = Instancio.create(RoleEntity.class);
        UserEntity user = userConverter.convertToUserEntity(mock(SignUpRequestDto.class), role);
        assertEquals(1, user.getRoles().size());
        assertEquals(role, user.getRoles().iterator().next());
    }

    @Test
    public void testConvertToSignUpResponseDto_withId_convertResponseCorrectly() {
        UserEntity userEntity = Instancio.create(UserEntity.class);
        SignUpResponseDto signUpResponseDto = userConverter.convertToSignUpResponseDto(userEntity);
        assertEquals(userEntity.getId(), signUpResponseDto.getId());
    }
}