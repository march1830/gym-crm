package com.yourcompany.gym.config;

import com.yourcompany.gym.dao.Storage;
import com.yourcompany.gym.dao.TraineeStorage;
import com.yourcompany.gym.model.Trainee;
import com.yourcompany.gym.model.Trainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = "com.yourcompany.gym")
public class AppConfig {

//    @Bean
//    public Storage<Trainee> traineeStorage() {
//        return new Storage<>();
//    }
//
//    @Bean
//    public Storage<Trainer> trainerStorage() {
//        return new Storage<>();
//    }
}