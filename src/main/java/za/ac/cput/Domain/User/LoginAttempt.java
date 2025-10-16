package za.ac.cput.Domain.User;

import java.time.LocalDateTime;

public class LoginAttempt {
    private final String email;
    private final int attempts;
    private final LocalDateTime lastAttempt;
    private final LocalDateTime lockTime;
    private final boolean isLocked;

    private LoginAttempt(Builder builder) {
        this.email = builder.email;
        this.attempts = builder.attempts;
        this.lastAttempt = builder.lastAttempt;
        this.lockTime = builder.lockTime;
        this.isLocked = builder.isLocked;
    }

    // Getters
    public String getEmail() { return email; }
    public int getAttempts() { return attempts; }
    public LocalDateTime getLastAttempt() { return lastAttempt; }
    public LocalDateTime getLockTime() { return lockTime; }
    public boolean isLocked() { return isLocked; }

    @Override
    public String toString() {
        return "LoginAttempt{" +
                "email='" + email + '\'' +
                ", attempts=" + attempts +
                ", lastAttempt=" + lastAttempt +
                ", lockTime=" + lockTime +
                ", isLocked=" + isLocked +
                '}';
    }

    public static class Builder {
        private String email;
        private int attempts;
        private LocalDateTime lastAttempt;
        private LocalDateTime lockTime;
        private boolean isLocked;

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setAttempts(int attempts) {
            this.attempts = attempts;
            return this;
        }

        public Builder setLastAttempt(LocalDateTime lastAttempt) {
            this.lastAttempt = lastAttempt;
            return this;
        }

        public Builder setLockTime(LocalDateTime lockTime) {
            this.lockTime = lockTime;
            return this;
        }

        public Builder setIsLocked(boolean isLocked) {
            this.isLocked = isLocked;
            return this;
        }

        public Builder copy(LoginAttempt loginAttempt) {
            this.email = loginAttempt.email;
            this.attempts = loginAttempt.attempts;
            this.lastAttempt = loginAttempt.lastAttempt;
            this.lockTime = loginAttempt.lockTime;
            this.isLocked = loginAttempt.isLocked;
            return this;
        }

        public LoginAttempt build() {
            return new LoginAttempt(this);
        }
    }


}