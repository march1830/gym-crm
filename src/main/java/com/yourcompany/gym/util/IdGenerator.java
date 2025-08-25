package com.yourcompany.gym.util;

import org.springframework.stereotype.Component;
import java.util.concurrent.atomic.AtomicLong;

@Component // <-- Очень важная аннотация!
public class IdGenerator {

    private final AtomicLong idCounter = new AtomicLong(0);

    public Long getNextId() {
        return idCounter.incrementAndGet();
    }
}