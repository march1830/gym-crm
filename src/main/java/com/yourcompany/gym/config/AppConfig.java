package com.yourcompany.gym.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

@Configuration
public class AppConfig {

    /**
     * Creates a PasswordEncoder bean to be used for hashing passwords.
     * This is a specific application need, so we define it manually.
     * Spring Boot will automatically inject this bean wherever a PasswordEncoder is required.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean // <-- This annotation marks the method as a bean definition
    public RestTemplate restTemplate() {
        // Creates a RestTemplate bean that can be injected elsewhere
        return new RestTemplate();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        // spring's default auth manager
        return config.getAuthenticationManager();
    }


}