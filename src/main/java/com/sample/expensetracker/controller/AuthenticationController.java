package com.sample.expensetracker.controller;

import com.sample.expensetracker.api.LoginRequestDto;
import com.sample.expensetracker.api.LoginResponseDto;
import com.sample.expensetracker.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author M.khoshnevisan
 * @since 9/12/2024
 */
@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto requestDto) {
        return authenticationService.login(requestDto);
    }
}