package za.ac.cput.Service.impl;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class LoginAttemptService {

    private final int MAX_ATTEMPTS = 5;
    private final long LOCK_TIME_DURATION = 5 * 60 * 1000; // 5 minutes in milliseconds

    // Store login attempts: key=email, value=LoginAttempt object
    private Map<String, LoginAttempt> loginAttempts = new ConcurrentHashMap<>();

    public static class LoginAttempt {
        private int attempts;
        private LocalDateTime lastAttempt;
        private LocalDateTime lockTime;

        public LoginAttempt() {
            this.attempts = 0;
            this.lastAttempt = LocalDateTime.now();
        }

        // Getters and setters
        public int getAttempts() { return attempts; }
        public void setAttempts(int attempts) { this.attempts = attempts; }
        public LocalDateTime getLastAttempt() { return lastAttempt; }
        public void setLastAttempt(LocalDateTime lastAttempt) { this.lastAttempt = lastAttempt; }
        public LocalDateTime getLockTime() { return lockTime; }
        public void setLockTime(LocalDateTime lockTime) { this.lockTime = lockTime; }
    }

    public void loginSucceeded(String email) {
        loginAttempts.remove(email); // Clear attempts on successful login
        System.out.println("✅ LOGIN SUCCESS: Reset attempts for: " + email);
    }

    public void loginFailed(String email) {
        LoginAttempt attempt = loginAttempts.getOrDefault(email, new LoginAttempt());
        int currentAttempts = attempt.getAttempts() + 1;
        attempt.setAttempts(currentAttempts);
        attempt.setLastAttempt(LocalDateTime.now());

        System.out.println("❌ LOGIN FAILED: Attempt " + currentAttempts + "/" + MAX_ATTEMPTS + " for: " + email);

        if (currentAttempts >= MAX_ATTEMPTS) {
            attempt.setLockTime(LocalDateTime.now());
            System.out.println("🔒 ACCOUNT LOCKED: " + email + " - Too many failed attempts");
        }

        loginAttempts.put(email, attempt);
    }

    public boolean isBlocked(String email) {
        LoginAttempt attempt = loginAttempts.get(email);
        if (attempt == null) {
            return false;
        }

        if (attempt.getLockTime() != null) {
            // Check if lock time has expired
            if (LocalDateTime.now().isBefore(attempt.getLockTime().plusMinutes(5))) {
                System.out.println("🔒 ACCOUNT STILL LOCKED: " + email);
                return true; // Still locked
            } else {
                // Lock time expired, reset attempts
                System.out.println("🔓 LOCK EXPIRED: Resetting attempts for: " + email);
                loginAttempts.remove(email);
                return false;
            }
        }

        return false;
    }

    public long getRemainingLockTime(String email) {
        LoginAttempt attempt = loginAttempts.get(email);
        if (attempt == null || attempt.getLockTime() == null) {
            return 0;
        }

        LocalDateTime unlockTime = attempt.getLockTime().plusMinutes(5);
        long remaining = java.time.Duration.between(LocalDateTime.now(), unlockTime).toSeconds();
        System.out.println("⏰ REMAINING LOCK TIME for " + email + ": " + remaining + " seconds");
        return Math.max(0, remaining);
    }

    public int getRemainingAttempts(String email) {
        LoginAttempt attempt = loginAttempts.get(email);
        if (attempt == null) {
            System.out.println("🎯 REMAINING ATTEMPTS for " + email + ": " + MAX_ATTEMPTS + " (first attempt)");
            return MAX_ATTEMPTS;
        }
        int remaining = Math.max(0, MAX_ATTEMPTS - attempt.getAttempts());
        System.out.println("🎯 REMAINING ATTEMPTS for " + email + ": " + remaining);
        return remaining;
    }

    // Add this method for debugging
    public void printAllAttempts() {
        System.out.println("📊 CURRENT LOGIN ATTEMPTS CACHE:");
        loginAttempts.forEach((email, attempt) -> {
            System.out.println("   " + email + ": " + attempt.getAttempts() + " attempts, locked: " + (attempt.getLockTime() != null));
        });
    }
}