package com.yourcompany.gym.facade;

import com.yourcompany.gym.model.Trainee;
import com.yourcompany.gym.service.TraineeService;
import com.yourcompany.gym.service.TrainerService;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component // Помечаем фасад как компонент, чтобы Spring мог его найти и создать
public class GymFacade {

    private final TraineeService traineeService;
    private final TrainerService trainerService;

    // Внедряем наши сервисы через конструктор
    public GymFacade(TraineeService traineeService, TrainerService trainerService) {
        this.traineeService = traineeService;
        this.trainerService = trainerService;
    }

    // --- Методы для Trainee ---

    public Trainee createTrainee(String firstName, String lastName, LocalDate dateOfBirth, String address) {
        // Просто вызываем соответствующий метод из сервиса
        return traineeService.createTraineeProfile(firstName, lastName, dateOfBirth, address);
    }

    // TODO: Добавить сюда остальные методы фасада для вызова других методов сервисов

}

