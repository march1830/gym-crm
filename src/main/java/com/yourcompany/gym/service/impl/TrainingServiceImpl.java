package com.yourcompany.gym.service.impl;

import com.yourcompany.gym.model.Training;
import com.yourcompany.gym.repository.TrainingRepository;
import com.yourcompany.gym.service.TrainingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TrainingServiceImpl implements TrainingService {

    private final TrainingRepository trainingRepository;

    @Autowired
    public TrainingServiceImpl(TrainingRepository trainingRepository) {
        this.trainingRepository = trainingRepository;
    }

    @Override
    @Transactional
    public Training create(Training training) {
        log.info("Creating a new training for trainee ID: {} and trainer ID: {}",
                training.getTrainee().getId(), training.getTrainer().getId());

        // Простая валидация
        if (training == null) {
            log.error("Attempted to create a null training.");
            throw new IllegalArgumentException("Training object cannot be null.");
        }

        Training savedTraining = trainingRepository.save(training);
        log.info("Successfully created training with ID: {}", savedTraining.getId());
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
}