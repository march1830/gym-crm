package com.yourcompany.gym.service;

import com.yourcompany.gym.model.Training;
import java.util.List;
import java.util.Optional;

public interface TrainingService {

    /**
     * Создает новую тренировку в системе.
     * @param training объект Training для сохранения
     * @return сохраненный объект Training с присвоенным ID
     */
    Training create(Training training);

    /**
     * Находит тренировку по ее уникальному идентификатору.
     * @param id ID тренировки
     * @return Optional, содержащий тренировку, если она найдена, иначе пустой Optional
     */
    Optional<Training> findById(Long id);

    /**
     * Возвращает список всех тренировок.
     * @return список всех тренировок
     */
    List<Training> findAll();
}