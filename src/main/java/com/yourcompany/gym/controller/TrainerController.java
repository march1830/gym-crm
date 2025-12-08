package com.yourcompany.gym.controller;

import com.yourcompany.gym.dto.*;
import com.yourcompany.gym.facade.GymFacade;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/trainers")
public class TrainerController {

    private final GymFacade gymFacade;

    public TrainerController(GymFacade gymFacade) {
        this.gymFacade = gymFacade;
    }

    @GetMapping("/{username}")
    public ResponseEntity<TrainerProfileResponse> getTrainerProfile(@PathVariable String username) {

        var profile = gymFacade.getTrainerProfile(username);
        return ResponseEntity.ok(profile);
    }

    @PutMapping("/{username}")
    public ResponseEntity<TrainerProfileResponse> updateTrainerProfile(
            @PathVariable String username,
            @Valid @RequestBody UpdateTrainerRequest request,
            Principal principal) {

        if (!principal.getName().equals(username)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        if (!username.equals(request.username())) {
            return ResponseEntity.badRequest().build();
        }

        var updatedProfile = gymFacade.updateTrainer(request);
        return ResponseEntity.ok(updatedProfile);
    }

    @PatchMapping("/{username}/status")
    public ResponseEntity<Void> updateTrainerStatus(
            @PathVariable String username,
            @Valid @RequestBody UpdateActiveStatusRequest request,
            Principal principal) { // Use Principal for security.

        if (!principal.getName().equals(username)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        gymFacade.setUserActiveStatus(username, request.isActive());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{username}/trainings")
    public ResponseEntity<List<TrainingResponseDTO>> getTrainerTrainings(
            @PathVariable String username,
            @RequestParam(required = false) LocalDate fromDate,
            @RequestParam(required = false) LocalDate toDate,
            @RequestParam(required = false) String traineeName) {

        List<TrainingResponseDTO> trainings = gymFacade.getTrainerTrainings(username, fromDate, toDate, traineeName);
        return ResponseEntity.ok(trainings);
    }
}