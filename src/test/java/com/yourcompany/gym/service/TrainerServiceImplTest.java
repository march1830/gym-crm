package com.yourcompany.gym.service;

import com.yourcompany.gym.model.Trainee;
import com.yourcompany.gym.model.Trainer;
import com.yourcompany.gym.repository.TraineeRepository;
import com.yourcompany.gym.repository.TrainerRepository;
import com.yourcompany.gym.service.impl.TrainerServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrainerServiceImplTest {

    @Mock
    private TrainerRepository trainerRepository;
    @Mock
    private TraineeRepository traineeRepository;

    @InjectMocks
    private TrainerServiceImpl trainerService;

    @Test
    void getUnassignedTrainers_ShouldReturnTrainersNotInTraineesList() {

        String traineeUsername = "test.trainee";

        Trainer assignedTrainer = new Trainer();
        assignedTrainer.setId(1L);
        Trainer unassignedTrainer = new Trainer();
        unassignedTrainer.setId(2L);
        List<Trainer> allActiveTrainers = List.of(assignedTrainer, unassignedTrainer);


        Trainee trainee = new Trainee();
        trainee.setTrainers(new HashSet<>(Set.of(assignedTrainer)));

        when(trainerRepository.findAllByIsActive(true)).thenReturn(allActiveTrainers);
        when(traineeRepository.findByUsername(traineeUsername)).thenReturn(Optional.of(trainee));


        List<Trainer> result = trainerService.getUnassignedTrainers(traineeUsername);


        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(unassignedTrainer.getId(), result.get(0).getId());
    }
}
