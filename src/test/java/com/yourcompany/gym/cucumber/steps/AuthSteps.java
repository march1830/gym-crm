package com.yourcompany.gym.cucumber.steps;

import com.yourcompany.gym.cucumber.TestContext;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.HashMap;
import java.util.Map;

public class AuthSteps {

    @Autowired private TestRestTemplate restTemplate;
    @Autowired private TestContext context;

    @When("User logins as trainee {string} {string}")
    public void loginSuccess(String firstName, String lastName) {
        Map<String, String> loginRequest = new HashMap<>();

        loginRequest.put("username", context.getSavedTraineeUsername());
        loginRequest.put("password", context.getSavedPassword());

        ResponseEntity<Map> response = restTemplate.postForEntity(
                "/api/auth/login", loginRequest, Map.class
        );

        if (response.getBody() != null) {
            context.setJwtToken((String) response.getBody().get("token"));
        }
        context.setLastResponse(new ResponseEntity<>(response.getStatusCode()));
    }

    @Then("User receives a valid JWT token")
    public void checkToken() {
        String token = context.getJwtToken();
        Assertions.assertNotNull(token);
        Assertions.assertTrue(token.startsWith("eyJ"));
    }

    @When("User tries to login with wrong password")
    public void loginFail() {
        Map<String, String> loginRequest = new HashMap<>();
        loginRequest.put("username", context.getSavedTraineeUsername());
        loginRequest.put("password", "WRONG_PASSWORD_123");

        ResponseEntity<String> response = restTemplate.postForEntity(
                "/api/auth/login", loginRequest, String.class
        );
        context.setLastResponse(response);
    }


}
