package com.yourcompany.gym.utils;

import org.springframework.stereotype.Component;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class IdGenerator {

    private final AtomicLong idCounter = new AtomicLong(0);

    public Long getNextId() {
        return idCounter.incrementAndGet();
    }
}