package com.yourcompany.gym.service;

import com.yourcompany.gym.model.Trainee;
import com.yourcompany.gym.model.Trainer;
import com.yourcompany.gym.model.TrainingType;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TrainerService {

    /**
     * Создает новый профиль тренера в системе.
     * Генерирует уникальный username и случайный пароль.
     * @param firstName Имя тренера
     * @param lastName Фамилия тренера
     * @param specialization Специализация тренера (тип тренировки)
     * @return Сохраненный объект Trainer с присвоенным ID
     */
    Trainer createTrainerProfile(String firstName,
                                 String lastName,
                                 TrainingType specialization);

    /**
     * Обновляет профиль существующего тренера.
     * @param username Имя пользователя тренера, которого нужно обновить.
     * @param firstName Новое имя тренера.
     * @param lastName Новая фамилия тренера.
     * @param specialization Новая специализация тренера.
     * @param isActive Новый статус активности.
     * @return Обновленный объект Trainee.
     */
    Trainer updateTrainerProfile(String username,
                                 String firstName,
                                 String lastName,
                                 TrainingType specialization,
                                 boolean isActive);

    /**
     * Находит тренера по его уникальному идентификатору.
     * @param id ID тренера
     * @return Optional, содержащий тренера, если он найден, иначе пустой Optional
     */
    Optional<Trainer> findById(Long id);

    /**
     * Находит профиль тренера по его имени пользователя.
     * @param username Имя пользователя для поиска.
     * @return Optional, содержащий профиль стажера, если найден.
     */
    Optional<Trainer> selectTraineeProfileByUsername(String username);;

    /**
     * Возвращает список всех тренеров.
     * @return список всех тренеров
     */
    List<Trainer> findAll();

    /**
     * Удаляет тренера по его уникальному идентификатору.
     * @param id ID тренера для удаления
     */
    void deleteById(Long id);

    List<Trainer> getUnassignedTrainers(String traineeUsername);
}