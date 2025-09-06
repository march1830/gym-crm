package com.yourcompany.gym.service;

public interface UserService {
    /**
     * Изменяет пароль пользователя после проверки старого пароля.
     * @param username Имя пользователя.
     * @param oldPassword Старый пароль для проверки.
     * @param newPassword Новый пароль для установки.
     */
    void changePassword(String username, String oldPassword, String newPassword);

    /**
     * Изменяет статус активности пользователя.
     * @param username ...
     * @param isActive ...
     */
    void setActiveStatus(String username, boolean isActive);
}