package com.yourcompany.gym.controller;

import com.yourcompany.gym.dto.RegistrationResponse;
import com.yourcompany.gym.dto.TraineeRegistrationRequest;
import com.yourcompany.gym.dto.TrainerRegistrationRequest;
import com.yourcompany.gym.facade.GymFacade;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/register")
public class RegistrationController {

    private final GymFacade gymFacade;

    public RegistrationController(GymFacade gymFacade) {
        this.gymFacade = gymFacade;
    }

    @PostMapping("/trainee")
    public ResponseEntity<RegistrationResponse> registerTrainee(@Valid @RequestBody TraineeRegistrationRequest request) {
        var response = gymFacade.createTrainee(
                request.firstName(),
                request.lastName(),
                request.dateOfBirth(),
                request.address()
        );

               URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/trainees/{username}")
                .buildAndExpand(response.username())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @PostMapping("/trainer")
    public ResponseEntity<RegistrationResponse> registerTrainer(@Valid @RequestBody TrainerRegistrationRequest request) {
        var response = gymFacade.createTrainer(
                request.firstName(),
                request.lastName(),
                request.specializationId()
        );


                URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/trainers/{username}")
                .buildAndExpand(response.username())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }
}
