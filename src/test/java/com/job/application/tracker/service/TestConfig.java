package com.job.application.tracker.service;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import com.job.application.tracker.service.implementation.EmailService;

import static org.mockito.Mockito.mock;

@TestConfiguration
public class TestConfig {

    @Bean
    @Primary
    public EmailService emailService() {
        return mock(EmailService.class);
    }
}