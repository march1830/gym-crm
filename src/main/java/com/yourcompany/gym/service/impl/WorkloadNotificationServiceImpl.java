package com.yourcompany.gym.service.impl;

import com.yourcompany.gym.dto.TrainerWorkloadRequest;
import com.yourcompany.gym.exception.WorkloadServiceUnavailableException;
import com.yourcompany.gym.model.Trainer;
import com.yourcompany.gym.model.Training;
import com.yourcompany.gym.security.JwtService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
@Slf4j
public class WorkloadNotificationServiceImpl {

    private final RestTemplate restTemplate;
    private final RestTemplate workLoadServiceRestTemplate;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @CircuitBreaker(name = "workloadService", fallbackMethod = "fallbackSendWorkload")
    public void sendWorkload(Trainer trainer, Training savedTraining) {
        TrainerWorkloadRequest workloadRequest = getTrainerWorkloadRequest(trainer, savedTraining);

        UserDetails userDetails = this.userDetailsService.loadUserByUsername(trainer.getUsername());

        String token = jwtService.generateToken(userDetails);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " +  token);
        HttpEntity<TrainerWorkloadRequest> requestEntity = new HttpEntity<>(workloadRequest, headers);

        String workloadUrl = "http://WORKLOAD-SERVICE/api/v1/workload";
        restTemplate.postForEntity(workloadUrl, requestEntity, Void.class);
    }

    private TrainerWorkloadRequest getTrainerWorkloadRequest(Trainer trainer, Training savedTraining) {
        TrainerWorkloadRequest workloadRequest = new TrainerWorkloadRequest();
        workloadRequest.setTrainerUsername(trainer.getUsername());
        workloadRequest.setTrainerFirstName(trainer.getFirstName());
        workloadRequest.setTrainerLastName(trainer.getLastName());
        workloadRequest.setIsActive(trainer.isActive());
        workloadRequest.setTrainingDate(savedTraining.getTrainingDate());
        workloadRequest.setTrainingDuration((long) savedTraining.getTrainingDuration());
        workloadRequest.setActionType("ADD");
        return workloadRequest;
    }

    private void fallbackSendWorkload(Trainer trainer, Training savedTraining, Throwable exception) {
        log.warn("Workload service is down or busy. Could not send update for trainer {}. Error: {}",
                trainer.getUsername(), exception.getMessage());
        throw new WorkloadServiceUnavailableException("Workload service is unavailable. Training created, but summary update failed.");
    }

}
