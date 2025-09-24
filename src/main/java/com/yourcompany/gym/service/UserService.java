package com.yourcompany.gym.service;

public interface UserService {
    /**
     * Changes a user's password after verifying the old password.
     * @param username The user's username.
     * @param newPassword The new password to set.
     */
    void changePassword(String username, String newPassword);

    /**
     * Changes the active status of a user.
     * @param username The user's username.
     * @param isActive The new active status (true for active, false for inactive).
     */
    void setActiveStatus(String username, boolean isActive);
}