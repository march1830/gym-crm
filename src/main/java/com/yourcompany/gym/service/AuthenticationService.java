package com.yourcompany.gym.service;

public interface AuthenticationService {
    /**
     * Checks if the provided credentials match a user in the system.
     * @param username The user's username.
     * @param password The raw, unencrypted password.
     * @return true if authentication is successful, otherwise false.
     */
    boolean checkCredentials(String username, String password);
}
