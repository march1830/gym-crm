package com.yourcompany.gym.service.impl;

import com.yourcompany.gym.model.Trainee;
import com.yourcompany.gym.repository.TraineeRepository;
import com.yourcompany.gym.repository.UserRepository;
import com.yourcompany.gym.service.TraineeService;
import lombok.extern.slf4j.Slf4j; // <-- Для логирования
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // <-- Для управления транзакциями
import java.util.List;
import java.util.Optional;

import java.security.SecureRandom;
import java.time.LocalDate;

@Slf4j // <-- Аннотация Lombok для автоматического создания логгера
@Service
public class TraineeServiceImpl implements TraineeService {

    // --- Новые зависимости от репозиториев ---
    private final TraineeRepository traineeRepository;
    private final UserRepository userRepository;

    // Используем constructor-based injection, как требовалось в задании
    @Autowired
    public TraineeServiceImpl(TraineeRepository traineeRepository, UserRepository userRepository) {
        this.traineeRepository = traineeRepository;
        this.userRepository = userRepository;
    }

    // Здесь мы реализуем наш метод
    @Override
    @Transactional // (Заметка #15) Вся операция будет одной транзакцией
    public Trainee createTraineeProfile(String firstName, String lastName, LocalDate dateOfBirth, String address) {
        log.info("Attempting to create a trainee profile for: {} {}", firstName, lastName);

        // (Заметка #3) Валидация обязательных полей
        if (firstName == null || lastName == null || firstName.isBlank() || lastName.isBlank()) {
            log.error("Validation failed: First name and last name are required.");
            throw new IllegalArgumentException("First name and last name are required.");
        }

        Trainee trainee = new Trainee();
        trainee.setFirstName(firstName);
        trainee.setLastName(lastName);
        trainee.setDateOfBirth(dateOfBirth);
        trainee.setAddress(address);
        trainee.setActive(true);

        // (Заметка #1) Генерация Username
        String username = generateUsername(firstName, lastName);
        trainee.setUsername(username);
        log.debug("Generated username: {}", username);

        // (Заметка #1) Генерация Password
        String password = generateRandomPassword(10);
        // ВАЖНО: В реальном проекте здесь нужно хешировать пароль перед установкой!
        // trainee.setPassword(passwordEncoder.encode(password));
        trainee.setPassword(password);

        // Сохраняем нового стажера в базу данных
        Trainee savedTrainee = traineeRepository.save(trainee);
        log.info("Successfully created trainee with ID: {} and username: {}", savedTrainee.getId(), savedTrainee.getUsername());

        // Возвращаем созданный объект (в идеале - DTO без пароля)
        return savedTrainee;
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
    public Optional<Trainee> findById(Long id) {
        // Просто вызываем готовый метод из JpaRepository
        return traineeRepository.findById(id);
    }

    @Override
    public Optional<Trainee> findByUsername(String username) {
        // Вызываем наш кастомный метод из TraineeRepository
        return traineeRepository.findByUserUsername(username);
    }

    @Override
    public List<Trainee> findAll() {
        // Просто вызываем готовый метод из JpaRepository
        return traineeRepository.findAll();
    }

    @Override
    @Transactional
    public Trainee update(Trainee trainee) {
        // Метод save() в JPA работает и для создания, и для обновления.
        // Если у объекта trainee есть ID, JPA выполнит UPDATE, а не INSERT.
        return traineeRepository.save(trainee);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        // Просто вызываем готовый метод из JpaRepository
        traineeRepository.deleteById(id);
    }
}