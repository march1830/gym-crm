package com.yourcompany.gym.facade;

import com.yourcompany.gym.model.Trainee;
import com.yourcompany.gym.model.Trainer;
import com.yourcompany.gym.model.Training;
import com.yourcompany.gym.model.TrainingType;
import com.yourcompany.gym.service.TraineeService;
import com.yourcompany.gym.service.TrainerService;
import com.yourcompany.gym.service.TrainingService;
import com.yourcompany.gym.service.UserService;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Component // Помечаем фасад как компонент, чтобы Spring мог его найти и создать
public class GymFacade {

    private final TraineeService traineeService;
    private final TrainerService trainerService;
    private final UserService userService;
    private final TrainingService trainingService;

    // Внедряем наши сервисы через конструктор
    public GymFacade(TraineeService traineeService, TrainerService trainerService, UserService userService, TrainingService trainingService) {
        this.traineeService = traineeService;
        this.trainerService = trainerService;
        this.userService = userService;
        this.trainingService = trainingService;
    }

    // --- Методы для Trainee ---

    public Trainee createTrainee(String firstName, String lastName, LocalDate dateOfBirth, String address) {
        // Просто вызываем соответствующий метод из сервиса
        return traineeService.createTraineeProfile(firstName, lastName, dateOfBirth, address);
    }

    public Trainee updateTrainee(String username, String firstName, String lastName, LocalDate dateOfBirth, String address, boolean isActive) {
        return traineeService.updateTraineeProfile(username, firstName, lastName, dateOfBirth, address, isActive);
    }

    public void deleteTraineeByUsername(String username) {
        traineeService.deleteProfileByUsername(username);
    }

    // --- Методы для Trainer ---
    public Trainer createTrainer(String firstName, String lastName, TrainingType specialization) {
        // Просто вызываем соответствующий метод из сервиса
        return trainerService.createTrainerProfile(firstName, lastName, specialization);
    }

    public Trainer updateTrainer(String username, String firstName, String lastName, TrainingType specialization, boolean isActive) {
        return trainerService.updateTrainerProfile(username, firstName, lastName, specialization, isActive);
    }

    // --- Методы для Training ---
    public Training addTraining(String traineeUsername, String trainerUsername, String trainingName, LocalDate trainingDate, int trainingDuration) {
        return trainingService.addTraining(traineeUsername, trainerUsername, trainingName, trainingDate, trainingDuration);
    }

    // --- Общие методы для User ---
    public void changeUserPassword(String username, String oldPassword, String newPassword) {
        userService.changePassword(username, oldPassword, newPassword);
    }
    public void setUserActiveStatus(String username, boolean isActive) {
        userService.setActiveStatus(username, isActive);
    }

    public List<Training> getTraineeTrainings(String username, LocalDate fromDate, LocalDate toDate, String trainerName, String trainingType) {
        return trainingService.getTraineeTrainingsByCriteria(username, fromDate, toDate, trainerName, trainingType);
    }
    public List<Training> getTrainerTrainings(String username, LocalDate fromDate, LocalDate toDate, String traineeName) {
        return trainingService.getTrainerTrainingsByCriteria(username, fromDate, toDate, traineeName);
    }
    public List<Trainer> getUnassignedTrainers(String traineeUsername) {
        return trainerService.getUnassignedTrainers(traineeUsername);
    }
    public Set<Trainer> updateTraineeTrainers(String traineeUsername, List<String> trainerUsernames) {
        return traineeService.updateTrainersList(traineeUsername, trainerUsernames);
    }

}






