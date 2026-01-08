package com.yourcompany.gym.cucumber.steps;

import com.yourcompany.gym.cucumber.TestContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.HashMap;
import java.util.Map;

public class RegistrationSteps {

    @Autowired private TestRestTemplate restTemplate;
    @Autowired private TestContext context;

    @Given("User registers a new trainee with name {string} {string}")
    public void registerTrainee(String firstName, String lastName) {
        Map<String, String> request = new HashMap<>();
        request.put("firstName", firstName);
        request.put("lastName", lastName);
        request.put("dateOfBirth", "1999-01-25");
        request.put("address", "Moscow");

        ResponseEntity<Map> response = restTemplate.postForEntity(
                "/api/register/trainee", request, Map.class
        );

        context.setSavedTraineeUsername((String) response.getBody().get("username"));
        context.setSavedPassword((String) response.getBody().get("password"));

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        context.setLastResponse(new ResponseEntity<>(response.getStatusCode()));
    }

    @Given("User registers a new trainer with name {string} {string}")
    public void registerTrainer(String firstName, String lastName) {
        Map<String, Object> request = new HashMap<>();
        request.put("firstName", firstName);
        request.put("lastName", lastName);
        request.put("specializationId", 2);

        ResponseEntity<Map> response = restTemplate.postForEntity(
                "/api/register/trainer", request, Map.class
        );

        context.setSavedTrainerUsername((String) response.getBody().get("username"));
        context.setLastResponse(new ResponseEntity<>(response.getStatusCode()));
    }
    @When("User tries to register a trainee with name {string} {string}")
    public void tryRegisterTrainee(String firstName, String lastName) {
        Map<String, String> request = new HashMap<>();
        request.put("firstName", firstName);
        request.put("lastName", lastName);
        request.put("dateOfBirth", "1999-01-25");
        request.put("address", "Moscow");

        ResponseEntity<String> response = restTemplate.postForEntity(
                "/api/register/trainee", request, String.class
        );

        context.setLastResponse(response);

    }
}

