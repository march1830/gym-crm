package com.yourcompany.gym.controller;

import com.yourcompany.gym.dto.AddTrainingRequest;
import com.yourcompany.gym.facade.GymFacade;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/trainings") // Base URL for training operations
public class TrainingController {

    private final GymFacade gymFacade;

    public TrainingController(GymFacade gymFacade) {
        this.gymFacade = gymFacade;
    }

    @PostMapping
    public ResponseEntity<Void> addTraining(
            @Valid @RequestBody AddTrainingRequest request,
            Principal principal) { // Inject the Principal to identify the logged-in user.

        // Security Check: Ensure the authenticated user (from Principal)
        // is the same trainee for whom the training is being added.
        if (!principal.getName().equals(request.traineeUsername())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        // The facade method is called without a password.
        gymFacade.addTraining(request);

        return ResponseEntity.ok().build();
    }
}