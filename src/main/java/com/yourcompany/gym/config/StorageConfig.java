package com.yourcompany.gym.config;

import com.yourcompany.gym.model.Trainee;
import com.yourcompany.gym.model.Trainer;
import com.yourcompany.gym.model.Training;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// 1. Класс-конфигурация
@Configuration
public class StorageConfig {

    // 2. Бин для хранения Trainee
    @Bean
    public Map<Long, Trainee> traineeStorage() {
        // 3. Используем потокобезопасную реализацию
        return new ConcurrentHashMap<>();
    }

    // 4. Бин для хранения Trainer
    @Bean
    public Map<Long, Trainer> trainerStorage() {
        return new ConcurrentHashMap<>();
    }

    // 5. Бин для хранения Training
    @Bean
    public Map<Long, Training> trainingStorage() {
        return new ConcurrentHashMap<>();
    }

}
