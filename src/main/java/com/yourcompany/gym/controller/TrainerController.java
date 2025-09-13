package com.yourcompany.gym.controller;

import com.yourcompany.gym.dto.LoginRequest;
import com.yourcompany.gym.dto.TrainerProfileResponse;
import com.yourcompany.gym.dto.UpdateActiveStatusRequest;
import com.yourcompany.gym.dto.UpdateTrainerRequest;
import com.yourcompany.gym.facade.GymFacade;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/trainers") // Base URL for all trainer-related operations
public class TrainerController {

    private final GymFacade gymFacade;

    public TrainerController(GymFacade gymFacade) {
        this.gymFacade = gymFacade;
    }

    @GetMapping("/{username}")
    public ResponseEntity<TrainerProfileResponse> getTrainerProfile(
            @PathVariable String username,
            @RequestBody LoginRequest authRequest) {

        var profile = gymFacade.getTrainerProfile(username, authRequest.password());

        return ResponseEntity.ok(profile);
    }
    @PutMapping("/{username}")
    public ResponseEntity<TrainerProfileResponse> updateTrainerProfile(
            @PathVariable String username,
            @Valid @RequestBody UpdateTrainerRequest request,
            @RequestBody LoginRequest authRequest) {

        if (!username.equals(request.username())) {
            return ResponseEntity.badRequest().build();
        }

        var updatedProfile = gymFacade.updateTrainer(request, authRequest.password());
        return ResponseEntity.ok(updatedProfile);
    }
    @PatchMapping("/{username}/status")
    public ResponseEntity<Void> updateTrainerStatus(
            @PathVariable String username,
            @Valid @RequestBody UpdateActiveStatusRequest request,
            @RequestBody LoginRequest authRequest) {

        if (!username.equals(authRequest.username())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        gymFacade.setUserActiveStatus(username, authRequest.password(), request.isActive());

        return ResponseEntity.ok().build();
    }
}