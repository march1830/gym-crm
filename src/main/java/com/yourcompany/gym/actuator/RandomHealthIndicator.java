package com.yourcompany.gym.actuator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

@Component // Mark this class as a Spring component
public class RandomHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        boolean isUp = ThreadLocalRandom.current().nextBoolean();

        if (isUp) {
            return Health.up().withDetail("reason", "Everything is fine today!").build();
        } else {
            return Health.down().withDetail("reason", "Something went wrong randomly.").build();
        }
    }
}
