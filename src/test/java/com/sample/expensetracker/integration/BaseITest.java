package com.sample.expensetracker.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tosan.http.server.starter.util.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.http.HttpHeaders;

/**
 * @author M.khoshnevisan
 * @since 9/16/2024
 */
public class BaseITest {

    protected HttpHeaders headers;

    @BeforeEach
    public void setup() {
        headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_LANGUAGE, "fa-IR");
        headers.add(Constants.X_FORWARDED_FOR, "192.168.2.1");
        headers.add(Constants.X_REQUEST_ID, "EX-998764");
    }
}