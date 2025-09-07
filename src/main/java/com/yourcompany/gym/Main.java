package com.yourcompany.gym;

import com.yourcompany.gym.config.AppConfig;
import com.yourcompany.gym.facade.GymFacade; // <-- Изменили импорт
import com.yourcompany.gym.model.Trainee;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        // 1. Загружаем нашу конфигурацию и запускаем Spring-контекст
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        // 2. Получаем наш ФАСАД из контекста
        GymFacade gymFacade = context.getBean(GymFacade.class);

        // 3. Вызываем метод фасада, чтобы проверить, работает ли сохранение в базу
        System.out.println("--- Creating new Trainee via Facade ---");
        Trainee newTrainee = gymFacade.createTrainee(
                "Jane", // Используем другие данные для новой записи
                "Smith",
                LocalDate.of(1998, 11, 22),
                "456 Power Ave."
        );

        System.out.println("--- Trainee Created Successfully ---");
        System.out.println("ID: " + newTrainee.getId());
        System.out.println("Username: " + newTrainee.getUsername());
        System.out.println("Password: " + newTrainee.getPassword());

        // 4. Закрываем контекст
        context.close();
    }
}