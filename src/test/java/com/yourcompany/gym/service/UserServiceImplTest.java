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



    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void changePassword_ShouldChangePasswordSuccessfully() {

        String username = "test.user";
        String newPassword = "newPassword";
        String newHashedPassword = "newHashedPassword";

        User user = new Trainee();
        user.setUsername(username);



        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(passwordEncoder.encode(newPassword)).thenReturn(newHashedPassword);


        userService.changePassword(username, newPassword);


        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();

        Assertions.assertEquals(newHashedPassword, savedUser.getPassword());
    }


    @Test
    void setActiveStatus_ShouldUpdateUserStatus() {

        String username = "test.user";
        boolean newStatus = false;

        User user = new Trainee();
        user.setActive(true);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));


        userService.setActiveStatus(username, newStatus);


        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();

        Assertions.assertEquals(newStatus, savedUser.isActive());
    }
}