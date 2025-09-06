package com.yourcompany.gym.service.impl;

import com.yourcompany.gym.model.User;
import com.yourcompany.gym.repository.UserRepository;
import com.yourcompany.gym.service.AuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean checkCredentials(String username, String password) {
        log.info("Checking credentials for username: {}", username);

        // 1. Находим пользователя по username
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isEmpty()) {
            log.warn("Authentication failed: User not found with username '{}'", username);
            return false; // Пользователь не найден
        }

        User user = userOptional.get();

        // 2. Сравниваем предоставленный пароль с хешем в базе
        // passwordEncoder.matches() сам выполняет "магию" BCrypt
        if (passwordEncoder.matches(password, user.getPassword())) {
            log.info("Authentication successful for username: {}", username);
            return true; // Пароли совпадают
        } else {
            log.warn("Authentication failed: Invalid password for username '{}'", username);
            return false; // Пароли не совпадают
        }
    }
}
