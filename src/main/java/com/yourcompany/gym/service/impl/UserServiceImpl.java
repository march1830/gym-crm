package com.yourcompany.gym.service.impl;

import com.yourcompany.gym.model.User;
import com.yourcompany.gym.repository.UserRepository;
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

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    // The method signature is now updated to match the interface and the facade's call.
    public void changePassword(String username, String newPassword) {
        log.info("Attempting to change password for user: {}", username);

        // The old password check is no longer needed here.
        // Spring Security has already authenticated the user before this method is called.

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username)); // This should not happen if the user is authenticated

        // We just encode the new password and save it.
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