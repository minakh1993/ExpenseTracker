package com.sample.expensetracker.service;

import com.sample.expensetracker.api.LoginRequestDto;
import com.sample.expensetracker.api.LoginResponseDto;
import com.sample.expensetracker.assembler.UserAssembler;
import com.sample.expensetracker.entity.UserEntity;
import com.sample.expensetracker.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @author M.khoshnevisan
 * @since 9/12/2024
 */
@Service
@AllArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserAssembler userAssembler;

    public LoginResponseDto login(LoginRequestDto requestDto) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                requestDto.getUsername(),
                requestDto.getPassword()
        ));
        UserEntity principal = (UserEntity) authenticate.getPrincipal();
        String jwt = jwtUtil.generateJWT(new HashMap<>() {{
            put("nationalCode", principal.getNationalCode());
        }});
        return userAssembler.convertToLoginResponseDto(principal, jwt);
    }
}