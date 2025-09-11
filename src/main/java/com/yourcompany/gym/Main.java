package com.yourcompany.gym;

import com.yourcompany.gym.config.AppConfig;
import com.yourcompany.gym.facade.GymFacade;
import com.yourcompany.gym.model.Trainee;
import com.yourcompany.gym.model.Trainer;
import com.yourcompany.gym.model.Training;
import com.yourcompany.gym.model.TrainingType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;
import java.util.List;

public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        GymFacade gymFacade = context.getBean(GymFacade.class);
        log.info("--- Application Context Loaded Successfully ---");


        TrainingType cardioType = new TrainingType();
        cardioType.setTrainingTypeName("Cardio");
        TrainingType strengthType = new TrainingType();
        strengthType.setTrainingTypeName("Strength");


        log.info("\n--- 1. Creating Trainers ---");
        Trainer trainer1 = gymFacade.createTrainer("Mike", "Mentzer", cardioType);
        Trainer trainer2 = gymFacade.createTrainer("Dorian", "Yates", strengthType);
        log.info("Created Trainer 1: {}", trainer1.getUsername());
        log.info("Created Trainer 2: {}", trainer2.getUsername());


        log.info("\n--- 2. Creating Trainee ---");
        Trainee trainee = gymFacade.createTrainee("Tom", "Platz", LocalDate.of(1955, 6, 26), "Venice, CA");
        String traineeUsername = trainee.getUsername();
        String traineePassword = trainee.getPassword();
        log.info("Created Trainee: {} with password: {}", traineeUsername, traineePassword);


        log.info("\n--- 3. Updating Trainee Profile (with correct password) ---");
        gymFacade.updateTrainee(traineeUsername, traineePassword, "Tommy", "Platz", trainee.getDateOfBirth(), "Gold's Gym, Venice", true);
        log.info("Trainee profile updated successfully.");


        log.info("\n--- 4. Testing Failed Authentication ---");
        try {
            gymFacade.selectTrainee(traineeUsername, "wrong_password");
        } catch (SecurityException e) {
            log.warn("Caught expected security exception: {}", e.getMessage());
        }


        log.info("\n--- 5. Testing Successful Authentication ---");
        Trainee selectedTrainee = gymFacade.selectTrainee(traineeUsername, traineePassword);
        log.info("Successfully selected trainee: {}", selectedTrainee.getFirstName());


        context.close();
    }
}