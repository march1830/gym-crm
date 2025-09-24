package com.yourcompany.gym.actuator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class ExternalApiServiceHealthIndicator implements HealthIndicator {

    private static final String EXTERNAL_API_URL = "https://api.publicapis.org/random";
    private final RestTemplate restTemplate; // <-- Use RestTemplate instead of HttpURLConnection

    // Inject the RestTemplate bean via the constructor
    public ExternalApiServiceHealthIndicator(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Health health() {
        try {
            // Use RestTemplate to make the GET request
            ResponseEntity<String> response = restTemplate.getForEntity(EXTERNAL_API_URL, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                // If the response code is 2xx (e.g., 200 OK), the service is UP
                return Health.up().withDetail("url", EXTERNAL_API_URL).withDetail("status", "Available").build();
            } else {
                // If any other code, it's DOWN
                return Health.down()
                        .withDetail("url", EXTERNAL_API_URL)
                        .withDetail("status", "Unavailable")
                        .withDetail("http_status", response.getStatusCode())
                        .build();
            }
        } catch (RestClientException e) {
            // If an exception occurs (e.g., timeout, host not found), it's DOWN
            return Health.down(e)
                    .withDetail("url", EXTERNAL_API_URL)
                    .withDetail("error", e.getMessage())
                    .build();
        }
    }
}