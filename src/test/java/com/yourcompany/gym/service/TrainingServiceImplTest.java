package com.yourcompany.gym.service;

import com.yourcompany.gym.model.Trainee;
import com.yourcompany.gym.model.Trainer;
import com.yourcompany.gym.model.Training;
import com.yourcompany.gym.model.TrainingType;
import com.yourcompany.gym.repository.TraineeRepository;
import com.yourcompany.gym.repository.TrainerRepository;
import com.yourcompany.gym.repository.TrainingRepository;
import com.yourcompany.gym.service.impl.TrainingServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrainingServiceImplTest {

    @Mock
    private TrainingRepository trainingRepository;
    @Mock
    private TraineeRepository traineeRepository;
    @Mock
    private TrainerRepository trainerRepository;

    @InjectMocks
    private TrainingServiceImpl trainingService;

    @Test
    void addTraining_ShouldCreateAndSaveTraining() {
        // Arrange
        String traineeUsername = "test.trainee";
        String trainerUsername = "test.trainer";
        String trainingName = "Morning Run";
        LocalDate trainingDate = LocalDate.now();
        int duration = 60;

        Trainee trainee = new Trainee();
        trainee.setUsername(traineeUsername);

        TrainingType cardio = new TrainingType();
        cardio.setTrainingTypeName("Cardio");

        Trainer trainer = new Trainer();
        trainer.setUsername(trainerUsername);
        trainer.setSpecialization(cardio);

        when(traineeRepository.findByUsername(traineeUsername)).thenReturn(Optional.of(trainee));
        when(trainerRepository.findByUsername(trainerUsername)).thenReturn(Optional.of(trainer));
        when(trainingRepository.save(any(Training.class))).thenAnswer(invocation -> {
            Training trainingToSave = invocation.getArgument(0);
            trainingToSave.setId(1L); // Set a dummy ID for the test
            return trainingToSave;
        });

        // Act
        trainingService.addTraining(traineeUsername, trainerUsername, trainingName, trainingDate, duration);

        // Assert
        ArgumentCaptor<Training> trainingCaptor = ArgumentCaptor.forClass(Training.class);
        verify(trainingRepository).save(trainingCaptor.capture());

        Training savedTraining = trainingCaptor.getValue();
        Assertions.assertEquals(trainee, savedTraining.getTrainee());
        Assertions.assertEquals(trainer, savedTraining.getTrainer());
        Assertions.assertEquals(trainingName, savedTraining.getTrainingName());
        Assertions.assertEquals(cardio, savedTraining.getTrainingType());
    }
}