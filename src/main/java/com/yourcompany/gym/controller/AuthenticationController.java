// src/main/java/com/yourcompany/gym/controller/AuthenticationController.java
package com.yourcompany.gym.controller;

import com.yourcompany.gym.dto.AuthenticationRequest;
import com.yourcompany.gym.dto.AuthenticationResponse;
import com.yourcompany.gym.security.JwtService;
import com.yourcompany.gym.security.LoginAttemptService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final LoginAttemptService loginAttemptService;
    private final HttpServletRequest request;
    private final UserDetailsService userDetailsService; // <-- Add this
    private final JwtService jwtService;             // <-- Add this

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest authRequest) {
        final String clientIp = loginAttemptService.getClientIP(request);
        if (loginAttemptService.isBlocked(clientIp)) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                    .body(new AuthenticationResponse("Error: Too many failed attempts."));
        }

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password())
            );

            // auth is successful, reset attempts
            loginAttemptService.loginSucceeded(clientIp);

            // find user details
            final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.username());

            // generate token
            final String jwt = jwtService.generateToken(userDetails);

            // return token in response
            return ResponseEntity.ok(new AuthenticationResponse(jwt));

        } catch (BadCredentialsException e) {
            // auth failed
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthenticationResponse("Error: Invalid username or password."));
        }
    }
}
