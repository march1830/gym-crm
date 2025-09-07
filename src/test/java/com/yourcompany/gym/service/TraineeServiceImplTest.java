package com.yourcompany.gym.service;

import com.yourcompany.gym.model.Trainee;
import com.yourcompany.gym.repository.TraineeRepository;
import com.yourcompany.gym.repository.UserRepository;
import com.yourcompany.gym.service.impl.TraineeServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) // Включаем поддержку Mockito
class TraineeServiceImplTest {

    // Создаем "мок" (пустышку) для репозитория, который мы будем контролировать
    @Mock
    private TraineeRepository traineeRepository;

    @Mock
    private UserRepository userRepository;

    // Создаем реальный объект сервиса и автоматически внедряем в него моки выше
    @InjectMocks
    private TraineeServiceImpl traineeService;

    @Test
    void createTraineeProfile_shouldGenerateUniqueUsername_whenBaseUsernameExists() {
        // 1. Arrange (Подготовка)
        String firstName = "John";
        String lastName = "Doe";
        String baseUsername = "john.doe";
        String expectedUsername = "john.doe1";

        // "Обучаем" моки: говорим, как они должны себя вести
        // Когда userRepository спросят про "john.doe", он должен ответить "true" (занято)
        when(userRepository.existsByUsername(baseUsername)).thenReturn(true);
        // Когда спросят про "john.doe1", он должен ответить "false" (свободно)
        when(userRepository.existsByUsername(expectedUsername)).thenReturn(false);
        // Когда traineeRepository попросят сохранить любого Trainee, он должен просто вернуть того же Trainee
        when(traineeRepository.save(any(Trainee.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // 2. Act (Действие)
        // Вызываем реальный метод нашего сервиса
        traineeService.createTraineeProfile(firstName, lastName, null, null);

        // 3. Assert (Проверка)
        // Создаем "перехватчик" аргументов, чтобы поймать, какой именно объект Trainee был отправлен на сохранение
        ArgumentCaptor<Trainee> traineeCaptor = ArgumentCaptor.forClass(Trainee.class);
        // Проверяем, что метод save() был вызван ровно 1 раз
        verify(traineeRepository).save(traineeCaptor.capture());

        // Получаем перехваченный объект Trainee
        Trainee savedTrainee = traineeCaptor.getValue();
        // Проверяем, что у него сгенерировался правильный username
        Assertions.assertEquals(expectedUsername, savedTrainee.getUsername());
    }
}
