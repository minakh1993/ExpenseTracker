package com.sample.expensetracker.integration;

import com.sample.expensetracker.api.SignUpRequestDto;
import com.sample.expensetracker.api.SignUpResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.hibernate.validator.internal.util.Contracts.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author M.khoshnevisan
 * @since 9/17/2024
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class SignUpITest extends BaseITest {

    public static final String SIGNUP_URL = "/management/user/signup";

    @BeforeEach
    public void setup() {
        restTemplate.getRestTemplate().setErrorHandler(new DefaultResponseErrorHandler());
        login();
    }

    @Test
    public void signUp_testConcurrency_lockSecondRequestCorrectly() throws ExecutionException, InterruptedException {
        SignUpRequestDto dto = createSignUpRequestDto();
        HttpEntity<SignUpRequestDto> request = new HttpEntity<>(dto, headers);
        ExecutorService executor = Executors.newFixedThreadPool(2);
        Future<ResponseEntity<SignUpResponseDto>> firstServiceCall = executor.submit(() -> restTemplate
                .postForEntity(SIGNUP_URL, request, SignUpResponseDto.class));
        Future<ResponseEntity<SignUpResponseDto>> secondServiceCall = executor.submit(() -> restTemplate
                .postForEntity(SIGNUP_URL, request, SignUpResponseDto.class));
        int exceptionCount = 0;
        Exception exception = null;
        try {
            ResponseEntity<SignUpResponseDto> firstSignUpResponse = firstServiceCall.get();
        } catch (Exception e) {
            exceptionCount++;
            exception = e;
        }
        try {
            ResponseEntity<SignUpResponseDto> secondSignUpResponse = secondServiceCall.get();
        } catch (Exception e) {
            exceptionCount++;
            exception = e;
        }
        assertEquals(1, exceptionCount);
        assertTrue(exception.getCause().getMessage().contains("EntityAlreadyExistException"), "EntityAlreadyExistException");
    }

    private SignUpRequestDto createSignUpRequestDto() {
        SignUpRequestDto signUpRequestDto = new SignUpRequestDto();
        signUpRequestDto.setName("testUser");
        signUpRequestDto.setFamily("testFamily");
        signUpRequestDto.setUsername("testUsername");
        signUpRequestDto.setPassword("fdsjf484940");
        signUpRequestDto.setNationalCode("0038476358");
        return signUpRequestDto;
    }
}