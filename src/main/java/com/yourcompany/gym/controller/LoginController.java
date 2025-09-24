package com.yourcompany.gym.controller;

import com.yourcompany.gym.dto.ChangePasswordRequest;
import com.yourcompany.gym.facade.GymFacade;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/credentials")
public class LoginController {

    private final GymFacade gymFacade;

    public LoginController(GymFacade gymFacade) {
        this.gymFacade = gymFacade;
    }

    @PutMapping("/change")
    public ResponseEntity<Void> changePassword(
            @Valid @RequestBody ChangePasswordRequest request,
            Principal principal) {

        // We get the username from the secure Principal object, not from the request body.
        gymFacade.changeUserPassword(principal.getName(), request.newPassword());
        return ResponseEntity.ok().build();
    }
    @GetMapping("/login")
    public ResponseEntity<Void> loginCheck() {
        return ResponseEntity.ok().build();
    }
}
