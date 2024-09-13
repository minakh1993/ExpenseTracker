package com.sample.expensetracker.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sample.expensetracker.util.JsonUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @author M.khoshnevisan
 * @since 9/13/2024
 */
@Configuration
public class ApplicationConfiguration {

    @Bean(name = "jsonMapper")
    @Primary
    public ObjectMapper jsonMapper() {
        return JsonUtil.getMapperInstance();
    }
}