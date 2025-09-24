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
@RequestMapping("/api/trainers") // Base URL for all trainer-related operations
public class TrainerController {

    private final GymFacade gymFacade;

    public TrainerController(GymFacade gymFacade) {
        this.gymFacade = gymFacade;
    }

    @GetMapping("/{username}")
    public ResponseEntity<TrainerProfileResponse> getTrainerProfile(@PathVariable String username) {
        // The password check is now handled by Spring Security's HttpBasic.
        var profile = gymFacade.getTrainerProfile(username);
        return ResponseEntity.ok(profile);
    }

    @PutMapping("/{username}")
    public ResponseEntity<TrainerProfileResponse> updateTrainerProfile(
            @PathVariable String username,
            @Valid @RequestBody UpdateTrainerRequest request,
            Principal principal) { // Use Principal to get the authenticated user.

        // Security Check: Ensure the logged-in user is the one they are trying to update.
        if (!principal.getName().equals(username)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        if (!username.equals(request.username())) {
            return ResponseEntity.badRequest().build();
        }

        // Call the updated facade method which no longer requires a password.
        var updatedProfile = gymFacade.updateTrainer(request);
        return ResponseEntity.ok(updatedProfile);
    }

    @PatchMapping("/{username}/status")
    public ResponseEntity<Void> updateTrainerStatus(
            @PathVariable String username,
            @Valid @RequestBody UpdateActiveStatusRequest request,
            Principal principal) { // Use Principal for security.

        // Security Check: A trainer can only change their own active status.
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

        // No password needed in the method call. Spring Security protects the endpoint.
        List<TrainingResponseDTO> trainings = gymFacade.getTrainerTrainings(username, fromDate, toDate, traineeName);
        return ResponseEntity.ok(trainings);
    }
}