package com.yourcompany.gym;

import com.yourcompany.gym.config.AppConfig;
import com.yourcompany.gym.facade.GymFacade;
import com.yourcompany.gym.model.Trainee;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        GymFacade gymFacade = context.getBean(GymFacade.class);

        System.out.println("--- Creating a new Trainee profile ---");
        Trainee newTrainee = new Trainee();
        newTrainee.setFirstName("Jane");
        newTrainee.setLastName("Doe");

        Trainee createdProfile = gymFacade.createTrainee(newTrainee);

        System.out.println("--- Profile Created Successfully ---");
        System.out.println("Generated Username: " + createdProfile.getUsername());
        System.out.println("Generated Password: " + createdProfile.getPassword());
        System.out.println("------------------------------------");
    }
}
