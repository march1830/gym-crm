package com.yourcompany.gym.actuator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) // Enable Mockito for JUnit 5
class ExternalApiServiceHealthIndicatorTest {

    @Mock // Create a mock (fake) RestTemplate
    private RestTemplate restTemplate;

    @InjectMocks // Create an instance of our HealthIndicator and inject the mock RestTemplate into it
    private ExternalApiServiceHealthIndicator healthIndicator;

    @Test
    void shouldReturnUpWhenServiceIsAvailable() {
        // Arrange: Define the behavior of the mock
        // When getForEntity is called, return a successful 200 OK response
        String url = "https://api.publicapis.org/random";
        when(restTemplate.getForEntity(url, String.class))
                .thenReturn(new ResponseEntity<>(HttpStatus.OK));

        // Act: Call the method we are testing
        Health health = healthIndicator.health();

        // Assert: Check the result
        // The status should be UP
        assertEquals(Status.UP, health.getStatus());
    }

    @Test
    void shouldReturnDownWhenServiceReturnsError() {
        // Arrange: Simulate the external service returning a 503 error
        String url = "https://api.publicapis.org/random";
        when(restTemplate.getForEntity(url, String.class))
                .thenReturn(new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE));

        // Act
        Health health = healthIndicator.health();

        // Assert: The status should be DOWN
        assertEquals(Status.DOWN, health.getStatus());
        assertEquals("Unavailable", health.getDetails().get("status"));
    }

    @Test
    void shouldReturnDownWhenServiceThrowsException() {
        // Arrange: Simulate a network error (e.g., timeout)
        String url = "https://api.publicapis.org/random";
        when(restTemplate.getForEntity(url, String.class))
                .thenThrow(new RestClientException("Connection timed out"));

        // Act
        Health health = healthIndicator.health();

        // Assert: The status should be DOWN and contain the error message
        assertEquals(Status.DOWN, health.getStatus());
        assertEquals("Connection timed out", health.getDetails().get("error"));
    }
}