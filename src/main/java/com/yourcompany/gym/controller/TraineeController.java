package com.yourcompany.gym.controller;

import com.yourcompany.gym.dto.LoginRequest; // We'll reuse this for authentication
import com.yourcompany.gym.dto.TraineeProfileResponse;
import com.yourcompany.gym.dto.UpdateActiveStatusRequest;
import com.yourcompany.gym.dto.UpdateTraineeRequest;
import com.yourcompany.gym.facade.GymFacade;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
