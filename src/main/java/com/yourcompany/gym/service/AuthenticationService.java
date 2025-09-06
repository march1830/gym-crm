package com.yourcompany.gym.service;

public interface AuthenticationService {
    /**
     * Проверяет, соответствуют ли предоставленные учетные данные пользователю в системе.
     * @param username Имя пользователя.
     * @param password Пароль в открытом виде.
     * @return true, если аутентификация прошла успешно, иначе false.
     */
    boolean checkCredentials(String username, String password);
}
