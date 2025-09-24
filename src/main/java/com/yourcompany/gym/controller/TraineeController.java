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
@RequestMapping("/api/trainees")
public class TraineeController {

    private final GymFacade gymFacade;

    public TraineeController(GymFacade gymFacade) {
        this.gymFacade = gymFacade;
    }

    @GetMapping("/{username}")
    public ResponseEntity<TraineeProfileResponse> getTraineeProfile(@PathVariable String username) {
        var profile = gymFacade.getTraineeProfile(username);
        return ResponseEntity.ok(profile);
    }

    @PutMapping("/{username}")
    public ResponseEntity<TraineeProfileResponse> updateTraineeProfile(
            @PathVariable String username,
            @Valid @RequestBody UpdateTraineeRequest request,
            Principal principal) {

        if (!principal.getName().equals(username)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        if (!username.equals(request.username())) {
            return ResponseEntity.badRequest().build();
        }

        var updatedProfile = gymFacade.updateTrainee(request);
        return ResponseEntity.ok(updatedProfile);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Void> deleteTraineeProfile(@PathVariable String username, Principal principal) {
        if (!principal.getName().equals(username)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        gymFacade.deleteTrainee(username);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{username}/status")
    public ResponseEntity<Void> updateTraineeStatus(
            @PathVariable String username,
            @Valid @RequestBody UpdateActiveStatusRequest request,
            Principal principal) {

        if (!principal.getName().equals(username)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        gymFacade.setUserActiveStatus(username, request.isActive());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{username}/trainers/unassigned")
    public ResponseEntity<List<TrainerInfo>> getUnassignedTrainers(@PathVariable String username) {
        List<TrainerInfo> unassignedTrainers = gymFacade.getUnassignedTrainersForTrainee(username);
        return ResponseEntity.ok(unassignedTrainers);
    }

    @PutMapping("/{username}/trainers")
    public ResponseEntity<List<TrainerInfo>> updateTraineeTrainers(
            @PathVariable String username,
            @Valid @RequestBody UpdateTraineeTrainersRequest request,
            Principal principal) {

        if (!principal.getName().equals(username)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        var updatedTrainers = gymFacade.updateTraineeTrainers(username, request.trainerUsernames());
        return ResponseEntity.ok(updatedTrainers);
    }

    @GetMapping("/{username}/trainings")
    public ResponseEntity<List<TrainingResponseDTO>> getTraineeTrainings(
            @PathVariable String username,
            @RequestParam(required = false) LocalDate fromDate,
            @RequestParam(required = false) LocalDate toDate,
            @RequestParam(required = false) String trainerName,
            @RequestParam(required = false) String trainingType) {

        List<TrainingResponseDTO> trainings = gymFacade.getTraineeTrainings(username, fromDate, toDate, trainerName, trainingType);
        return ResponseEntity.ok(trainings);
    }
}