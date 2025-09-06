package com.yourcompany.gym.service.impl;

import com.yourcompany.gym.model.Trainee;
import com.yourcompany.gym.model.Trainer;
import com.yourcompany.gym.model.TrainingType;
import com.yourcompany.gym.repository.TrainerRepository;
import com.yourcompany.gym.repository.UserRepository;
import com.yourcompany.gym.service.TrainerService;
import org.springframework.security.crypto.password.PasswordEncoder;
import lombok.extern.slf4j.Slf4j; // <-- Для логирования
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;// <-- Для управления транзакциями

import java.security.SecureRandom;

@Slf4j // <-- Аннотация Lombok для автоматического создания логгера
@Service
public class TrainerServiceImpl implements TrainerService {

    // --- Новые зависимости от репозиториев ---
    private final TrainerRepository trainerRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Используем constructor-based injection, как требовалось в задании
    @Autowired
    public TrainerServiceImpl(TrainerRepository trainerRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.trainerRepository = trainerRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Здесь мы реализуем наш метод
    @Override
    @Transactional // (Заметка #15) Вся операция будет одной транзакцией
    public Trainer createTrainerProfile(String firstName, String lastName, TrainingType specialization) {
        log.info("Attempting to create a trainer profile for: {} {}", firstName, lastName);

        // (Заметка #3) Валидация обязательных полей
        if (firstName == null || lastName == null || firstName.isBlank() || lastName.isBlank()) {
            log.error("Validation failed: First name and last name are required.");
            throw new IllegalArgumentException("First name and last name are required.");
        }

        Trainer trainer = new Trainer();
        trainer.setFirstName(firstName);
        trainer.setLastName(lastName);
        trainer.setSpecialization(specialization);
        trainer.setActive(true);

        // (Заметка #1) Генерация Username
        String username = generateUsername(firstName, lastName);
        trainer.setUsername(username);
        log.debug("Generated username: {}", username);

        // (Заметка #1) Генерация Password
        String password = generateRandomPassword(10);
        trainer.setPassword(passwordEncoder.encode(password));
        trainer.setPassword(password);

        // Сохраняем нового стажера в базу данных
        Trainer savedTrainer = trainerRepository.save(trainer);
        log.info("Successfully created trainer with ID: {} and username: {}", savedTrainer.getId(), savedTrainer.getUsername());

        // Возвращаем созданный объект (в идеале - DTO без пароля)
        return savedTrainer;
    }

    // --- Вспомогательные приватные методы ---

    private String generateUsername(String firstName, String lastName) {
        String baseUsername = firstName.toLowerCase() + "." + lastName.toLowerCase();
        String finalUsername = baseUsername;
        int suffix = 1;
        // Проверяем, существует ли уже такой username в базе
        while (userRepository.existsByUsername(finalUsername)) {
            finalUsername = baseUsername + suffix++;
        }
        return finalUsername;
    }

    private String generateRandomPassword(int length) {
        final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(CHARS.charAt(random.nextInt(CHARS.length())));
        }
        return sb.toString();
    }

    // --- РЕАЛИЗАЦИЯ ОСТАЛЬНЫХ МЕТОДОВ ИНТЕРФЕЙСА ---

    @Override
    public Optional<Trainer> findById(Long id) {
        // Просто вызываем готовый метод из JpaRepository
        return trainerRepository.findById(id);
    }

    @Override
    public Optional<Trainer> selectTraineeProfileByUsername(String username) {
        log.info("Selecting trainer profile by username: {}", username);
        // Мы просто вызываем метод, который уже создали в нашем TrainerRepository.
        return trainerRepository.findByUsername(username);
    }

    @Override
    public List<Trainer> findAll() {
        // Просто вызываем готовый метод из JpaRepository
        return trainerRepository.findAll();
    }

    @Override
    @Transactional // Обновление - это операция записи, нужна транзакция
    public Trainer updateTrainerProfile(String username, String firstName, String lastName, TrainingType specialization, boolean isActive) {
        log.info("Attempting to update profile for trainer with username: {}", username);

        // 1. Находим существующего стажера в базе данных
        Trainer trainerToUpdate = trainerRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.error("Trainer with username {} not found.", username);
                    return new RuntimeException("Trainer not found with username: " + username); // В будущем здесь будет кастомный Exception
                });

        // 2. Обновляем его поля
        trainerToUpdate.setFirstName(firstName);
        trainerToUpdate.setLastName(lastName);
        trainerToUpdate.setSpecialization(specialization);
        trainerToUpdate.setActive(isActive);

        // 3. Сохраняем изменения
        // Метод save() в JPA очень умный: если у объекта есть ID,
        // он выполнит SQL-команду UPDATE, а не INSERT.
        Trainer updatedTrainer = trainerRepository.save(trainerToUpdate);
        log.info("Successfully updated profile for trainee with ID: {}", updatedTrainer.getId());

        return updatedTrainer;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        // Просто вызываем готовый метод из JpaRepository
        trainerRepository.deleteById(id);
    }
}