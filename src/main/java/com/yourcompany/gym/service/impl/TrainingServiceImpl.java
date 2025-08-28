package com.yourcompany.gym.service.impl;

import com.yourcompany.gym.dao.TraineeDAO;
import com.yourcompany.gym.dao.TrainerDAO;
import com.yourcompany.gym.dao.TrainingDAO;
import com.yourcompany.gym.model.Training;
import com.yourcompany.gym.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrainingServiceImpl implements TrainingService {

    private final TrainingDAO trainingDAO;
    private final TraineeDAO traineeDAO;
    private final TrainerDAO trainerDAO;

    @Autowired
    public TrainingServiceImpl(TrainingDAO trainingDAO, TraineeDAO traineeDAO, TrainerDAO trainerDAO) {
        this.trainingDAO = trainingDAO;
        this.traineeDAO = traineeDAO;
        this.trainerDAO = trainerDAO;
    }

    @Override
    public Training create(Training training) {
        // Проверяем, что стажер существует
        traineeDAO.findById(training.getTrainee().getId())
                .orElseThrow(() -> new IllegalArgumentException("Trainee with id " + training.getTrainee().getId() + " not found."));

        // Проверяем, что тренер существует
        trainerDAO.findById(training.getTrainer().getId())
                .orElseThrow(() -> new IllegalArgumentException("Trainer with id " + training.getTrainer().getId() + " not found."));

        return trainingDAO.save(training);
    }

    @Override
    public Optional<Training> findById(Long id) {
        return trainingDAO.findById(id);
    }

    @Override
    public List<Training> findAll() {
        return trainingDAO.findAll();
    }
}