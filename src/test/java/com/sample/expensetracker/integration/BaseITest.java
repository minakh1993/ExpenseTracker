package com.sample.expensetracker.integration;

import com.sample.expensetracker.api.LoginRequestDto;
import com.sample.expensetracker.api.LoginResponseDto;
import com.tosan.http.server.starter.util.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import static com.sample.expensetracker.integration.AuthenticationITest.LOGIN_URL;

/**
 * @author M.khoshnevisan
 * @since 9/16/2024
 */
public class BaseITest {

    protected HttpHeaders headers = new HttpHeaders();
    @Autowired
    protected TestRestTemplate restTemplate;

    @BeforeEach
    public void setup() {
        headers.add(HttpHeaders.CONTENT_LANGUAGE, "fa-IR");
        headers.add(Constants.X_FORWARDED_FOR, "192.168.2.1");
        headers.add(Constants.X_REQUEST_ID, "EX-998764");
    }

    public void login() {
        LoginRequestDto dto = new LoginRequestDto();
        dto.setUsername("mina");
        dto.setPassword("mina");
        HttpEntity<LoginRequestDto> request = new HttpEntity<>(dto, headers);
        ResponseEntity<LoginResponseDto> responseEntity = restTemplate
                .postForEntity(LOGIN_URL, request, LoginResponseDto.class);
        LoginResponseDto body = responseEntity.getBody();
        headers.add("Authorization", "Bearer " + body.getJwtToken());
    }
}