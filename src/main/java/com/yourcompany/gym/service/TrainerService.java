package com.yourcompany.gym.service;

import com.yourcompany.gym.model.Trainer;
import java.util.List;
import java.util.Optional;

public interface TrainerService {

    /**
     * Создает нового тренера в системе.
     * @param trainer объект Trainer для сохранения
     * @return сохраненный объект Trainer с присвоенным ID
     */
    Trainer create(Trainer trainer);

    /**
     * Находит тренера по его уникальному идентификатору.
     * @param id ID тренера
     * @return Optional, содержащий тренера, если он найден, иначе пустой Optional
     */
    Optional<Trainer> findById(Long id);

    /**
     * Находит тренера по его имени пользователя.
     * @param username имя пользователя
     * @return Optional, содержащий тренера, если он найден, иначе пустой Optional
     */
    Optional<Trainer> findByUsername(String username);

    /**
     * Возвращает список всех тренеров.
     * @return список всех тренеров
     */
    List<Trainer> findAll();

    /**
     * Обновляет данные существующего тренера.
     * @param trainer объект Trainer с обновленными данными
     * @return обновленный объект Trainer
     */
    Trainer update(Trainer trainer);

    /**
     * Удаляет тренера по его уникальному идентификатору.
     * @param id ID тренера для удаления
     */
    void deleteById(Long id);
}