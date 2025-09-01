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
    Trainee createTraineeProfile(String firstName, String lastName, LocalDate dateOfBirth, String address);

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
    Optional<Trainee> findByUsername(String username);

    /**
     * Возвращает список всех стажеров.
     * @return список всех стажеров
     */
    List<Trainee> findAll();

    /**
     * Обновляет данные существующего стажера.
     * @param trainee объект Trainee с обновленными данными
     * @return обновленный объект Trainee
     */
    Trainee update(Trainee trainee);

    /**
     * Удаляет стажера по его уникальному идентификатору.
     * @param id ID стажера для удаления
     */
    void deleteById(Long id);


}