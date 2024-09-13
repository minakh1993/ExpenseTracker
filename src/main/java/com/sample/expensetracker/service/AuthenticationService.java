package com.sample.expensetracker.service;

import com.sample.expensetracker.api.LoginRequestDto;
import com.sample.expensetracker.api.LoginResponseDto;
import com.sample.expensetracker.converter.UserConverter;
import com.sample.expensetracker.entity.UserEntity;
import com.sample.expensetracker.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

import static com.sample.expensetracker.api.ClaimConstants.*;

/**
 * @author M.khoshnevisan
 * @since 9/12/2024
 */
@Service
@AllArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserConverter userConverter;

    public LoginResponseDto login(LoginRequestDto requestDto) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                requestDto.getUsername(),
                requestDto.getPassword()
        ));
        UserEntity principal = (UserEntity) authenticate.getPrincipal();
        String jwt = jwtUtil.generateJWT(new HashMap<>() {{
            put(USER_ID, principal.getId());
            put(NATIONAL_CODE, principal.getNationalCode());
            put(ROLES, principal.getAuthorities() != null ? principal.getAuthorities()
                    .stream().map(grantedAuthority -> grantedAuthority.getAuthority())
                    .collect(Collectors.toList()) : new ArrayList<>());
        }});
        return userConverter.convertToLoginResponseDto(principal, jwt);
    }
}