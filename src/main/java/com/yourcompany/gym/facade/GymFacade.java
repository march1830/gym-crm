package com.yourcompany.gym.facade;

import com.yourcompany.gym.model.Trainee;
import com.yourcompany.gym.model.Trainer;
import com.yourcompany.gym.model.TrainingType;
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

    public Trainee updateTrainee(String username, String firstName, String lastName, LocalDate dateOfBirth, String address, boolean isActive) {
        return traineeService.updateTraineeProfile(username, firstName, lastName, dateOfBirth, address, isActive);
    }

    // --- Методы для Trainer ---
    public Trainer createTrainer(String firstName, String lastName, TrainingType specialization) {
        // Просто вызываем соответствующий метод из сервиса
        return trainerService.createTrainerProfile(firstName, lastName, specialization);
    }

    public Trainer updateTrainer(String username, String firstName, String lastName, TrainingType specialization, boolean isActive) {
        return trainerService.updateTrainerProfile(username, firstName, lastName, specialization, isActive);
    }
}






