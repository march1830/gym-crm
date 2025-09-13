package com.yourcompany.gym.controller;

import com.yourcompany.gym.dto.ChangePasswordRequest;
import com.yourcompany.gym.dto.LoginRequest;
import com.yourcompany.gym.facade.GymFacade;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login")
public class LoginController {

    private final GymFacade gymFacade;

    public LoginController(GymFacade gymFacade) {
        this.gymFacade = gymFacade;
    }

    @PostMapping
    public ResponseEntity<Void> login(@Valid @RequestBody LoginRequest loginRequest) {
        boolean isAuthenticated = gymFacade.checkUserCredentials(
                loginRequest.username(),
                loginRequest.password()
        );

        if (isAuthenticated) {
            // return 200 OK upon successful login
            return ResponseEntity.ok().build();
        } else {
            // return 401 Unauthorized in case of an unsuccessful login
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    @PutMapping("/change")
    public ResponseEntity<Void> changePassword(@Valid @RequestBody ChangePasswordRequest request) {
        gymFacade.changeUserPassword(
                request.username(),
                request.oldPassword(),
                request.newPassword()
        );
        // If the service method doesn't throw an exception, it means success.
        return ResponseEntity.ok().build();
    }
}
