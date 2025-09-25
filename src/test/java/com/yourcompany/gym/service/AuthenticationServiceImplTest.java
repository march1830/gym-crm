package com.yourcompany.gym.service;

import com.yourcompany.gym.model.Trainee;
import com.yourcompany.gym.model.User;
import com.yourcompany.gym.repository.UserRepository;
import com.yourcompany.gym.service.impl.AuthenticationServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    @Test
    void checkCredentials_ShouldReturnTrue_WhenCredentialsAreValid() {
        // Arrange
        String username = "test.user";
        String rawPassword = "password123";
        String hashedPassword = "$2a$10$somebcryptstring";

        User user = new Trainee();
        user.setUsername(username);
        user.setPassword(hashedPassword);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(rawPassword, hashedPassword)).thenReturn(true);

        // Act
        boolean result = authenticationService.checkCredentials(username, rawPassword);

        // Assert
        Assertions.assertTrue(result);
    }

    @Test
    void checkCredentials_ShouldReturnFalse_WhenUserNotFound() {
        // Arrange
        String username = "non.existent.user";
        String password = "password123";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        // Act
        boolean result = authenticationService.checkCredentials(username, password);

        // Assert
        Assertions.assertFalse(result);
    }

    @Test
    void checkCredentials_ShouldReturnFalse_WhenPasswordIsInvalid() {
        // Arrange
        String username = "test.user";
        String rawPassword = "wrongPassword";
        String hashedPassword = "$2a$10$somebcryptstring";

        User user = new Trainee();
        user.setUsername(username);
        user.setPassword(hashedPassword);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(rawPassword, hashedPassword)).thenReturn(false);

        // Act
        boolean result = authenticationService.checkCredentials(username, rawPassword);

        // Assert
        Assertions.assertFalse(result);
    }
}