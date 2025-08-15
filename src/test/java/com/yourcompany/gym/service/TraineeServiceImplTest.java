package com.yourcompany.gym.service;

import com.yourcompany.gym.dao.TraineeDAO;
import com.yourcompany.gym.model.Trainee;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

class TraineeServiceImplTest {
    @Mock
    private TraineeDAO traineeDAO;

    @InjectMocks
    private TraineeServiceImpl traineeService;

    @Test
    void createTraineeProfile_shouldGenerateUniqueUsername_whenBaseUsernameExists() {

        Trainee traineeToCreate = new Trainee();
        traineeToCreate.setFirstName("John");
        traineeToCreate.setLastName("Doe");


        when(traineeDAO.findByUsername("john.doe")).thenReturn(Optional.of(new Trainee()));

        when(traineeDAO.findByUsername("john.doe1")).thenReturn(Optional.empty());

        when(traineeDAO.save(any(Trainee.class))).thenAnswer(invocation -> invocation.getArgument(0));


        Trainee createdTrainee = traineeService.createTraineeProfile(traineeToCreate);


        assertNotNull(createdTrainee);

        assertEquals("john.doe1", createdTrainee.getUsername());

        assertNotNull(createdTrainee.getPassword());
        assertEquals(10, createdTrainee.getPassword().length());

    }
}
