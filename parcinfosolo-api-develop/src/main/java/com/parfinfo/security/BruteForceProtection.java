package com.parfinfo.security;

import org.springframework.stereotype.Component;
import java.util.concurrent.ConcurrentHashMap;
import java.time.LocalDateTime;
import java.time.Duration;

@Component
public class BruteForceProtection {
    private final ConcurrentHashMap<String, LoginAttempt> attempts = new ConcurrentHashMap<>();
    private static final int MAX_ATTEMPTS = 5;
    private static final Duration LOCKOUT_DURATION = Duration.ofMinutes(15);

    public boolean isBlocked(String username) {
        LoginAttempt attempt = attempts.get(username);
        if (attempt == null) {
            return false;
        }
        
        if (attempt.isLocked() && attempt.getLockoutTime().plus(LOCKOUT_DURATION).isAfter(LocalDateTime.now())) {
            return true;
        }
        
        if (attempt.getLockoutTime().plus(LOCKOUT_DURATION).isBefore(LocalDateTime.now())) {
            attempts.remove(username);
            return false;
        }
        
        return false;
    }

    public void recordFailedAttempt(String username) {
        attempts.compute(username, (key, attempt) -> {
            if (attempt == null) {
                return new LoginAttempt(1, LocalDateTime.now());
            }
            
            if (attempt.isLocked() && attempt.getLockoutTime().plus(LOCKOUT_DURATION).isAfter(LocalDateTime.now())) {
                return attempt;
            }
            
            int newAttempts = attempt.getAttempts() + 1;
            LocalDateTime lockoutTime = newAttempts >= MAX_ATTEMPTS ? LocalDateTime.now() : null;
            
            return new LoginAttempt(newAttempts, lockoutTime);
        });
    }

    public void resetAttempts(String username) {
        attempts.remove(username);
    }

    private static class LoginAttempt {
        private final int attempts;
        private final LocalDateTime lockoutTime;

        public LoginAttempt(int attempts, LocalDateTime lockoutTime) {
            this.attempts = attempts;
            this.lockoutTime = lockoutTime;
        }

        public int getAttempts() {
            return attempts;
        }

        public LocalDateTime getLockoutTime() {
            return lockoutTime;
        }

        public boolean isLocked() {
            return lockoutTime != null;
        }
    }
} 