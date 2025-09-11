package com.yourcompany.gym.service;

package com.yourcompany.gym.service.impl;

import com.yourcompany.gym.model.Trainee;
import com.yourcompany.gym.model.User;
import com.yourcompany.gym.repository.UserRepository;
import com.yourcompany.gym.service.AuthenticationService;
import com.yourcompany.gym.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void changePassword_ShouldChangePassword_WhenOldPasswordIsValid() {
        // Arrange
        String username = "test.user";
        String oldPassword = "oldPassword";
        String newPassword = "newPassword";
        String newHashedPassword = "newHashedPassword";

        // FIXED: Instantiating a concrete subclass
        User user = new Trainee();
        user.setUsername(username);

        when(authenticationService.checkCredentials(username, oldPassword)).thenReturn(true);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(passwordEncoder.encode(newPassword)).thenReturn(newHashedPassword);

        // Act
        userService.changePassword(username, oldPassword, newPassword);

        // Assert
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();

        Assertions.assertEquals(newHashedPassword, savedUser.getPassword());
    }

    @Test
    void changePassword_ShouldThrowException_WhenOldPasswordIsInvalid() {
        // Arrange
        String username = "test.user";
        String oldPassword = "wrongOldPassword";
        String newPassword = "newPassword";

        when(authenticationService.checkCredentials(username, oldPassword)).thenReturn(false);

        // Act & Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.changePassword(username, oldPassword, newPassword);
        });

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void setActiveStatus_ShouldUpdateUserStatus() {
        // Arrange
        String username = "test.user";
        boolean newStatus = false;

        // FIXED: Instantiating a concrete subclass
        User user = new Trainee();
        user.setActive(true); // Initial status is active

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        // Act
        userService.setActiveStatus(username, newStatus);

        // Assert
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();

        Assertions.assertEquals(newStatus, savedUser.isActive());
    }
}
