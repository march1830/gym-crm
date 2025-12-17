package com.yourcompany.gym.cucumber.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.yourcompany.gym.cucumber.TestContext;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.jms.core.JmsTemplate;
import jakarta.jms.Message;

import java.util.HashMap;
import java.util.Map;

public class TrainingSteps {

    @Autowired private TestRestTemplate restTemplate;
    @Autowired private TestContext context;
    @Autowired private JmsTemplate jmsTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @When("User adds a training {string} with duration {int} for trainer {string} {string}")
    public void addTraining(String trainingName, int duration, String trainerFirst, String trainerLast) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(context.getJwtToken());

        Map<String, Object> body = new HashMap<>();
        body.put("traineeUsername", context.getSavedTraineeUsername());
        body.put("trainerUsername", context.getSavedTrainerUsername());
        body.put("trainingName", trainingName);
        body.put("trainingDate", "2026-04-26");
        body.put("trainingDuration", duration);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(
                "/api/trainings", entity, String.class
        );
        context.setLastResponse(response);
    }
    @When("User tries to add a training {string} with date {string}")
    public void tryAddTrainingWithDate(String trainingName, String dateString) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(context.getJwtToken());

        Map<String, Object> body = new HashMap<>();
        body.put("traineeUsername", context.getSavedTraineeUsername());
        body.put("trainerUsername", context.getSavedTrainerUsername());
        body.put("trainingName", trainingName);
        body.put("trainingDate", dateString);
        body.put("trainingDuration", 60);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(
                "/api/trainings", entity, String.class
        );

        context.setLastResponse(response);
    }

    @Then("The workload message should be sent to {string} queue with action {string}")
    public void verifyWorkloadMessage(String queueName, String actionType) throws Exception {
        jmsTemplate.setReceiveTimeout(5000);
        Object receivedObject = jmsTemplate.receiveAndConvert(queueName);
        Assertions.assertNotNull(receivedObject, "Queue " + queueName + " empty! The message was not sent.");

        Map<String, Object> messageData;

        if (receivedObject instanceof Map) {
            messageData = (Map<String, Object>) receivedObject;
        } else {
            messageData = objectMapper.convertValue(receivedObject, Map.class);
        }

        Assertions.assertEquals(context.getSavedTrainerUsername(), messageData.get("trainerUsername"), "Incorrect trainer's name in the message");
        Assertions.assertEquals(actionType, messageData.get("actionType"), "Incorrect action type");

        Number duration = (Number) messageData.get("trainingDuration");
        Assertions.assertEquals(60, duration.intValue(), "Incorrect training duration");
    }

}