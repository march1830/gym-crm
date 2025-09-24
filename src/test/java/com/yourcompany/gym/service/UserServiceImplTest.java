package com.yourcompany.gym.service;

import com.yourcompany.gym.model.Trainee;
import com.yourcompany.gym.model.User;
import com.yourcompany.gym.repository.UserRepository;
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

    // We no longer need AuthenticationService here, so it's removed.

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void changePassword_ShouldChangePasswordSuccessfully() {
        // Arrange
        String username = "test.user";
        String newPassword = "newPassword";
        String newHashedPassword = "newHashedPassword";

        User user = new Trainee();
        user.setUsername(username);

        // This stub is no longer needed and has been removed:
        // when(authenticationService.checkCredentials(username, oldPassword)).thenReturn(true);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(passwordEncoder.encode(newPassword)).thenReturn(newHashedPassword);

        // Act
        // We now call the updated method with two arguments.
        userService.changePassword(username, newPassword);

        // Assert
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();

        Assertions.assertEquals(newHashedPassword, savedUser.getPassword());
    }

    // This test for invalid old password is no longer relevant, as the service
    // doesn't check the old password anymore. It can be removed.

    @Test
    void setActiveStatus_ShouldUpdateUserStatus() {
        // Arrange
        String username = "test.user";
        boolean newStatus = false;

        User user = new Trainee();
        user.setActive(true);

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