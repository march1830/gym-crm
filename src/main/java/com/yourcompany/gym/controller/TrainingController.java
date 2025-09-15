package com.yourcompany.gym.controller;

import com.yourcompany.gym.dto.AddTrainingRequest;
import com.yourcompany.gym.facade.GymFacade;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/trainings") // Base URL for training operations
public class TrainingController {

    private final GymFacade gymFacade;

    public TrainingController(GymFacade gymFacade) {
        this.gymFacade = gymFacade;
    }

    /**
     * Endpoint to add a new training session.
     * The trainee's password must be provided for authentication.
     */
    @PostMapping
    public ResponseEntity<Void> addTraining(@Valid @RequestBody AddTrainingRequest request) {
        // The controller makes a single, clean call to the facade.
        // The facade will handle the authentication and business logic internally.
        gymFacade.addTraining(request);

        return ResponseEntity.ok().build();
    }
}