package com.yourcompany.gym.service;

import com.yourcompany.gym.model.Trainee;
import com.yourcompany.gym.model.Trainer;
import com.yourcompany.gym.repository.TraineeRepository;
import com.yourcompany.gym.repository.TrainerRepository;
import com.yourcompany.gym.repository.UserRepository;
import com.yourcompany.gym.service.impl.TraineeServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TraineeServiceImplTest {

    @Mock
    private TraineeRepository traineeRepository;
    @Mock
    private TrainerRepository trainerRepository; // Needed for updateTrainersList
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder; // Assuming it's needed for other tests

    @InjectMocks
    private TraineeServiceImpl traineeService;

    // Your existing createTraineeProfile test...

    @Test
    void updateProfile_ShouldUpdateTraineeDetails() {
        // Arrange
        String username = "test.trainee";
        Trainee existingTrainee = new Trainee();
        existingTrainee.setUsername(username);
        existingTrainee.setFirstName("OldName");

        when(traineeRepository.findByUsername(username)).thenReturn(Optional.of(existingTrainee));
        when(traineeRepository.save(any(Trainee.class))).thenAnswer(inv -> inv.getArgument(0));

        // Act
        Trainee updated = traineeService.updateTraineeProfile(username, "NewName", "LastName", LocalDate.now(), "Address", true);

        // Assert
        Assertions.assertEquals("NewName", updated.getFirstName());
        Assertions.assertEquals("Address", updated.getAddress());
        verify(traineeRepository).save(existingTrainee);
    }

    @Test
    void deleteProfileByUsername_ShouldDeleteTrainee() {
        // Arrange
        String username = "test.trainee";
        Trainee traineeToDelete = new Trainee();
        when(traineeRepository.findByUsername(username)).thenReturn(Optional.of(traineeToDelete));

        // Act
        traineeService.deleteProfileByUsername(username);

        // Assert
        verify(traineeRepository).delete(traineeToDelete);
    }

    @Test
    void updateTrainersList_ShouldUpdateTraineeTrainers() {
        // Arrange
        String traineeUsername = "test.trainee";
        List<String> trainerUsernames = List.of("test.trainer1", "test.trainer2");

        Trainee trainee = new Trainee();
        Trainer trainer1 = new Trainer();
        trainer1.setUsername("test.trainer1");
        Trainer trainer2 = new Trainer();
        trainer2.setUsername("test.trainer2");
        List<Trainer> trainers = List.of(trainer1, trainer2);

        when(traineeRepository.findByUsername(traineeUsername)).thenReturn(Optional.of(trainee));
        when(trainerRepository.findAllByUserUsernameIn(trainerUsernames)).thenReturn(trainers);

        // Act
        Set<Trainer> updatedTrainers = traineeService.updateTrainersList(traineeUsername, trainerUsernames);

        // Assert
        Assertions.assertEquals(2, updatedTrainers.size());
        verify(traineeRepository).save(trainee);
    }
}
