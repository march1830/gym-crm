package com.yourcompany.gym.controller;

import com.yourcompany.gym.dto.*;
import com.yourcompany.gym.facade.GymFacade;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/trainees") // Base URL for all trainee-related operations
public class TraineeController {

    private final GymFacade gymFacade;

    public TraineeController(GymFacade gymFacade) {
        this.gymFacade = gymFacade;
    }

    @GetMapping("/{username}") // The URL will be for example: /api/trainees/john.doe
    public ResponseEntity<TraineeProfileResponse> getTraineeProfile(
            @PathVariable String username,
            @RequestBody LoginRequest authRequest) { // We get username/password from the body

        // We use the username from the path for the action,
        // and the one from the body for authentication.
        // In a real security setup, this would come from a security context.
        var profile = gymFacade.getTraineeProfile(username, authRequest.password());

        return ResponseEntity.ok(profile);
    }
    @PutMapping("/{username}")
    public ResponseEntity<TraineeProfileResponse> updateTraineeProfile(
            @PathVariable String username,
            @Valid @RequestBody UpdateTraineeRequest request,
            // We get the password from a separate auth request object for clarity
            @RequestBody LoginRequest authRequest) {

        // Ensure the user in the path is the one being updated
        if (!username.equals(request.username())) {
            // In a real app, you might throw a custom exception here
            return ResponseEntity.badRequest().build();
        }

        var updatedProfile = gymFacade.updateTrainee(request, authRequest.password());
        return ResponseEntity.ok(updatedProfile);
    }
    @DeleteMapping("/{username}")
    public ResponseEntity<Void> deleteTraineeProfile(
            @PathVariable String username,
            @RequestBody LoginRequest authRequest) {

        // We use the username from the path for the action,
        // and the username from the body for authentication.
        // We should ensure they match.
        if (!username.equals(authRequest.username())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        gymFacade.deleteTrainee(username, authRequest.password());

        // Return 200 OK or 204 No Content for successful deletion
        return ResponseEntity.ok().build();
    }
    @PatchMapping("/{username}/status")
    public ResponseEntity<Void> updateTraineeStatus(
            @PathVariable String username,
            @Valid @RequestBody UpdateActiveStatusRequest request,
            @RequestBody LoginRequest authRequest) {

        if (!username.equals(authRequest.username())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        gymFacade.setUserActiveStatus(username, authRequest.password(), request.isActive());

        return ResponseEntity.ok().build();
    }
    @GetMapping("/{username}/trainers/unassigned")
    public ResponseEntity<List<TrainerInfo>> getUnassignedTrainers(
            @PathVariable String username,
            @RequestBody LoginRequest authRequest) {

        // We need to ensure the user making the request is the trainee in question
        if (!username.equals(authRequest.username())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        // We need a new method in the facade for this
        List<TrainerInfo> unassignedTrainers = gymFacade.getUnassignedTrainersForTrainee(username, authRequest.password());

        return ResponseEntity.ok(unassignedTrainers);
    }
    @PutMapping("/{username}/trainers")
    public ResponseEntity<List<TrainerInfo>> updateTraineeTrainers(
            @PathVariable String username,
            @Valid @RequestBody UpdateTraineeTrainersRequest request,
            @RequestBody LoginRequest authRequest) {

        if (!username.equals(authRequest.username())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        var updatedTrainers = gymFacade.updateTraineeTrainers(username, authRequest.password(), request.trainerUsernames());
        return ResponseEntity.ok(updatedTrainers);
    }
    @GetMapping("/{username}/trainings")
    public ResponseEntity<List<TrainingResponseDTO>> getTraineeTrainings(
            @PathVariable String username,
            @RequestParam(required = false) LocalDate fromDate,
            @RequestParam(required = false) LocalDate toDate,
            @RequestParam(required = false) String trainerName,
            @RequestParam(required = false) String trainingType,
            @RequestBody LoginRequest authRequest) {

        if (!username.equals(authRequest.username())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        List<TrainingResponseDTO> trainings = gymFacade.getTraineeTrainings(username, authRequest.password(), fromDate, toDate, trainerName, trainingType);
        return ResponseEntity.ok(trainings);
    }
}
