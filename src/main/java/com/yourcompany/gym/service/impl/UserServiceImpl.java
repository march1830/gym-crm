package com.yourcompany.gym.service.impl;

import com.yourcompany.gym.model.User;
import com.yourcompany.gym.repository.UserRepository;
import com.yourcompany.gym.service.AuthenticationService;
import com.yourcompany.gym.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationService authenticationService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationService authenticationService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationService = authenticationService;
    }

    @Override
    @Transactional
    public void changePassword(String username, String oldPassword, String newPassword) {
        log.info("Attempting to change password for user: {}", username);


        boolean credentialsValid = authenticationService.checkCredentials(username, oldPassword);
        if (!credentialsValid) {
            log.warn("Password change failed: Invalid old password for user {}", username);

            throw new IllegalArgumentException("Invalid old password.");
        }


        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username)); // Эта ошибка не должна произойти


        user.setPassword(passwordEncoder.encode(newPassword));


        userRepository.save(user);
        log.info("Password changed successfully for user: {}", username);
    }
    @Override
    @Transactional
    public void setActiveStatus(String username, boolean isActive) {
        log.info("Setting active status to {} for user: {}", isActive, username);


        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.error("User with username {} not found.", username);
                    return new RuntimeException("User not found: " + username);
                });


        user.setActive(isActive);


        userRepository.save(user);
        log.info("Successfully changed active status for user: {}", username);
    }
}
