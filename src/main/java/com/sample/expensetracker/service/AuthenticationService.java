package com.sample.expensetracker.service;

import com.sample.expensetracker.api.LoginRequestDto;
import com.sample.expensetracker.api.LoginResponseDto;
import com.sample.expensetracker.assembler.UserAssembler;
import com.sample.expensetracker.entity.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

/**
 * @author M.khoshnevisan
 * @since 9/12/2024
 */
@Service
@AllArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final UserAssembler userAssembler;

    public LoginResponseDto login(LoginRequestDto requestDto) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                requestDto.getUsername(),
                requestDto.getPassword()
        ));
        return userAssembler.convertToLoginResponseDto((UserEntity)authenticate.getPrincipal());
    }
}