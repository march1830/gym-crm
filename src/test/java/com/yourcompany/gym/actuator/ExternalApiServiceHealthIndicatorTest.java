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

@ExtendWith(MockitoExtension.class)
class ExternalApiServiceHealthIndicatorTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ExternalApiServiceHealthIndicator healthIndicator;

    @Test
    void shouldReturnUpWhenServiceIsAvailable() {

        String url = "https://api.publicapis.org/random";
        when(restTemplate.getForEntity(url, String.class))
                .thenReturn(new ResponseEntity<>(HttpStatus.OK));


        Health health = healthIndicator.health();


        assertEquals(Status.UP, health.getStatus());
    }

    @Test
    void shouldReturnDownWhenServiceReturnsError() {

        String url = "https://api.publicapis.org/random";
        when(restTemplate.getForEntity(url, String.class))
                .thenReturn(new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE));

                Health health = healthIndicator.health();

        assertEquals(Status.DOWN, health.getStatus());
        assertEquals("Unavailable", health.getDetails().get("status"));
    }

    @Test
    void shouldReturnDownWhenServiceThrowsException() {

        String url = "https://api.publicapis.org/random";
        when(restTemplate.getForEntity(url, String.class))
                .thenThrow(new RestClientException("Connection timed out"));

        Health health = healthIndicator.health();

        assertEquals(Status.DOWN, health.getStatus());
        assertEquals("Connection timed out", health.getDetails().get("error"));
    }
}