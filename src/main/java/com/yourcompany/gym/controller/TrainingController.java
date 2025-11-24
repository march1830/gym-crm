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
@RequestMapping("/api/trainings")
public class TrainingController {

    private final GymFacade gymFacade;

    public TrainingController(GymFacade gymFacade) {
        this.gymFacade = gymFacade;
    }

    @PostMapping
    public ResponseEntity<String> addTraining(
            @Valid @RequestBody AddTrainingRequest request,
            Principal principal) {

        if (!principal.getName().equals(request.traineeUsername())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        gymFacade.addTraining(request);

        return ResponseEntity.ok("The request has been sent for processing. Wait for execution.");
    }
}