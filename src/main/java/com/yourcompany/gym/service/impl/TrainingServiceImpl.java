package com.yourcompany.gym.service.impl;

import com.yourcompany.gym.model.Trainee;
import com.yourcompany.gym.model.Trainer;
import com.yourcompany.gym.model.Training;
import com.yourcompany.gym.model.TrainingType;
import com.yourcompany.gym.repository.TraineeRepository;
import com.yourcompany.gym.repository.TrainerRepository;
import com.yourcompany.gym.repository.TrainingRepository;
import com.yourcompany.gym.service.TrainingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TrainingServiceImpl implements TrainingService {

    private final TrainingRepository trainingRepository;
    private final TraineeRepository traineeRepository;
    private final TrainerRepository trainerRepository;

    @Autowired
    public TrainingServiceImpl(TrainingRepository trainingRepository, TraineeRepository traineeRepository, TrainerRepository trainerRepository) {
        this.trainingRepository = trainingRepository;
        this.traineeRepository = traineeRepository;
        this.trainerRepository = trainerRepository;
    }

    @Override
    @Transactional
    public Training addTraining(String traineeUsername, String trainerUsername, String trainingName, LocalDate trainingDate, int trainingDuration) {
        log.info("Attempting to add training '{}' for trainee '{}' with trainer '{}'",
                trainingName, traineeUsername, trainerUsername);

        // 1. Находим стажера по username
        Trainee trainee = traineeRepository.findByUsername(traineeUsername)
                .orElseThrow(() -> new RuntimeException("Trainee not found with username: " + traineeUsername));

        // 2. Находим тренера по username
        Trainer trainer = trainerRepository.findByUsername(trainerUsername)
                .orElseThrow(() -> new RuntimeException("Trainer not found with username: " + trainerUsername));

        // 3. Получаем тип тренировки из специализации тренера
        TrainingType trainingType = trainer.getSpecialization();

        // 4. Создаем новый объект тренировки
        Training newTraining = new Training();
        newTraining.setTrainee(trainee);
        newTraining.setTrainer(trainer);
        newTraining.setTrainingType(trainingType);
        newTraining.setTrainingName(trainingName);
        newTraining.setTrainingDate(trainingDate);
        newTraining.setTrainingDuration(trainingDuration);

        // 5. Сохраняем тренировку в базу данных
        Training savedTraining = trainingRepository.save(newTraining);
        log.info("Successfully added training with ID: {}", savedTraining.getId());

        return savedTraining;
    }

    @Override
    public Optional<Training> findById(Long id) {
        log.info("Finding training by ID: {}", id);
        return trainingRepository.findById(id);
    }

    @Override
    public List<Training> findAll() {
        log.info("Finding all trainings.");
        return trainingRepository.findAll();
    }
    @Override
    public List<Training> getTraineeTrainingsByCriteria(String username, LocalDate fromDate, LocalDate toDate, String trainerName, String trainingType) {
        log.info("Searching trainings for trainee {} with criteria.", username);
        return trainingRepository.findTrainingsByTraineeUsernameAndCriteria(username, fromDate, toDate, trainerName, trainingType);
    }
    @Override
    public List<Training> getTrainerTrainingsByCriteria(String username, LocalDate fromDate, LocalDate toDate, String traineeName) {
        log.info("Searching trainings for trainer {} with criteria.", username);
        return trainingRepository.findTrainingsByTrainerUsernameAndCriteria(username, fromDate, toDate, traineeName);
    }
}