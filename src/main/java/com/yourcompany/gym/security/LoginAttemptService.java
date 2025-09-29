package com.yourcompany.gym.security;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class LoginAttemptService {

    // block after 3 attempts
    private static final int MAX_ATTEMPTS = 3;

    // cache for storing attempts, expires after 5 mins
    private final LoadingCache<String, Integer> attemptsCache;

    public LoginAttemptService() {
        attemptsCache = Caffeine.newBuilder()
                .expireAfterWrite(5, TimeUnit.MINUTES)
                .build(key -> 0); // default value is 0
    }

    // called on successful login
    public void loginSucceeded(String key) {
        attemptsCache.invalidate(key);
    }

    // called on failed login
    public void loginFailed(String key) {
        int attempts = attemptsCache.get(key);
        attempts++;
        attemptsCache.put(key, attempts);
    }

    // check if an IP is blocked
    public boolean isBlocked(String key) {
        return attemptsCache.get(key) >= MAX_ATTEMPTS;
    }

    // helper to get client IP
    public String getClientIP(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null || xfHeader.isEmpty() || !xfHeader.contains(request.getRemoteAddr())) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }
}
