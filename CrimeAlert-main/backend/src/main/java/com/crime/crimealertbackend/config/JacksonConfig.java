package com.crime.crimealertbackend.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        System.out.println("✅ Custom ObjectMapper registered!");
        ObjectMapper mapper = new ObjectMapper();
        // Register support for Java 8 date/time types
        mapper.registerModule(new JavaTimeModule());
        // Optional: write dates in ISO format instead of timestamps
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }
}
