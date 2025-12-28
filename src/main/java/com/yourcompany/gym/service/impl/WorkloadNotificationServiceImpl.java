package com.yourcompany.gym.service.impl;

import com.yourcompany.gym.dto.TrainerWorkloadRequest;
import com.yourcompany.gym.exception.WorkloadServiceUnavailableException;
import com.yourcompany.gym.model.Trainer;
import com.yourcompany.gym.model.Training;
import com.yourcompany.gym.security.JwtService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
@Slf4j
public class WorkloadNotificationServiceImpl {

    private final JmsTemplate jmsTemplate;

    public void sendWorkload(Trainer trainer, Training savedTraining) {
        TrainerWorkloadRequest workloadRequest = getTrainerWorkloadRequest(trainer, savedTraining);
        String transactionId = MDC.get("transactionId");
        log.info("Sending workload update to ActiveMQ queue 'workload.topic' for trainer: {}", trainer.getUsername());

        try {
            jmsTemplate.convertAndSend("workload.topic", workloadRequest, message -> {
                if (transactionId != null) {
                    message.setStringProperty("transactionId", transactionId);
                }
                return message;
            });

            log.info("Message sent successfully");
        } catch (Exception e) {
            log.error("Failed to send message to ActiveMQ", e);
            throw e; 
        }
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
        String currentTransactionId = MDC.get("transactionId");

        return workloadRequest;
    }
}
