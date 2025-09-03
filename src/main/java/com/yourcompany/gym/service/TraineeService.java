package com.yourcompany.gym.service;

import com.yourcompany.gym.model.Trainee;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TraineeService {

    /**
     * Создает новый профиль стажера в системе.
     * Генерирует уникальный username и случайный пароль.
     * @param firstName Имя стажера
     * @param lastName Фамилия стажера
     * @param dateOfBirth Дата рождения стажера
     * @param address Адрес стажера (опционально)
     * @return Сохраненный объект Trainee с присвоенным ID
     */
    Trainee createTraineeProfile(String firstName,
                                 String lastName,
                                 LocalDate dateOfBirth,
                                 String address);

    /**
     * Обновляет профиль существующего стажера.
     * @param username Имя пользователя стажера, которого нужно обновить.
     * @param firstName Новое имя стажера.
     * @param lastName Новая фамилия стажера.
     * @param dateOfBirth Новая дата рождения.
     * @param address Новый адрес.
     * @param isActive Новый статус активности.
     * @return Обновленный объект Trainee.
     */
    Trainee updateTraineeProfile(String username,
                                 String firstName,
                                 String lastName,
                                 LocalDate dateOfBirth,
                                 String address,
                                 boolean isActive);

    /**
     * Находит стажера по его уникальному идентификатору.
     * @param id ID стажера
     * @return Optional, содержащий стажера, если он найден, иначе пустой Optional
     */
    Optional<Trainee> findById(Long id);

    /**
     * Находит стажера по его имени пользователя.
     * @param username имя пользователя
     * @return Optional, содержащий стажера, если он найден, иначе пустой Optional
     */
    Optional<Trainee> selectTraineeProfileByUsername(String username);

    /**
     * Удаляет профиль стажера по его имени пользователя.
     * @param username Имя пользователя для удаления.
     */
    void deleteProfileByUsername(String username);

    /**
     * Возвращает список всех стажеров.
     * @return список всех стажеров
     */
    List<Trainee> findAll();

    /**
     * Удаляет стажера по его уникальному идентификатору.
     * @param id ID стажера для удаления
     */
    void deleteById(Long id);


}