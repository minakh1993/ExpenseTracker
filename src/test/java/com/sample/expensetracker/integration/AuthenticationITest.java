package com.sample.expensetracker.integration;

import com.sample.expensetracker.api.LoginRequestDto;
import com.sample.expensetracker.api.LoginResponseDto;
import com.sample.expensetracker.api.UserInfoDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author M.khoshnevisan
 * @since 9/16/2024
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class AuthenticationITest extends BaseITest {

    @Autowired
    private TestRestTemplate restTemplate;

    public static final String LOGIN_URL = "/auth/login";

    @BeforeEach
    public void setup() {
        restTemplate.getRestTemplate().setErrorHandler(new DefaultResponseErrorHandler());
    }

    @Test
    public void login_testWithValidCredentials_returnValidLoginResponse() {
        LoginRequestDto dto = new LoginRequestDto();
        dto.setUsername("mina");
        dto.setPassword("mina");
        HttpEntity<LoginRequestDto> request = new HttpEntity<>(dto, headers);
        ResponseEntity<LoginResponseDto> responseEntity = restTemplate
                .postForEntity(LOGIN_URL, request, LoginResponseDto.class);
        LoginResponseDto body = responseEntity.getBody();
        UserInfoDto userInfoDto = body.getUserInfoDto();
        assertNotNull(userInfoDto);
        assertEquals("mina", userInfoDto.getName());
        assertEquals("kh", userInfoDto.getFamily());
        assertEquals("0083747893", userInfoDto.getNationalCode());
        log.info(String.valueOf(body));
    }

    @Test
    public void login_testWithInvalidCredentials_returnCredentialException() {
        LoginRequestDto dto = new LoginRequestDto();
        dto.setUsername("mina");
        dto.setPassword("1234");
        HttpEntity<LoginRequestDto> request = new HttpEntity<>(dto, headers);
        try {
            ResponseEntity<LoginResponseDto> responseEntity = restTemplate
                    .postForEntity(LOGIN_URL, request, LoginResponseDto.class);
        } catch (Exception e) {
            int value = ((HttpClientErrorException.Unauthorized) e).getStatusCode().value();
            assertEquals(401, value);
        }
    }
}