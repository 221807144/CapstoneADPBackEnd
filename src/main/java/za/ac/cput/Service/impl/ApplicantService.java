package za.ac.cput.Service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import za.ac.cput.Domain.User.Applicant;
import za.ac.cput.Repository.ApplicantRepository;
import za.ac.cput.Service.IApplicantService;

import java.util.List;
import java.util.Optional;

@Service
public class ApplicantService implements IApplicantService {

    private final ApplicantRepository applicantRepository;

private BCryptPasswordEncoder bCryptPasswordEncoder =  new BCryptPasswordEncoder(12);

    @Autowired
    private LoginAttemptService loginAttemptService;

    @Autowired
    public ApplicantService(ApplicantRepository applicantRepository) {
        this.applicantRepository = applicantRepository;
    }

    @Override
    public Applicant create(Applicant applicant) {
     applicant.setPassword(bCryptPasswordEncoder.encode(applicant.getPassword()));
        return applicantRepository.save(applicant);
    }

    @Override
    public Applicant read(Integer id) {
        Optional<Applicant> applicant = applicantRepository.findById(id);
        return applicant.orElse(null);
    }

    @Override
    public Applicant update(Applicant applicant) {
        if (applicantRepository.existsById(applicant.getUserId())) {
            return applicantRepository.save(applicant);
        }
        return null;
    }

    @Override
    public boolean delete(Integer id) {
        if (applicantRepository.existsById(id)) {
            applicantRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Applicant> getAll() {
        return applicantRepository.findAll();
    }

    public boolean validatePassword(String rawPassword, String encodedPassword) {
        return bCryptPasswordEncoder.matches(rawPassword, encodedPassword);
    }

    // New login validation method with attempt tracking
    public LoginResult validateLogin(String email, String rawPassword) {
        // Check if account is blocked
        if (loginAttemptService.isBlocked(email)) {
            long remainingTime = loginAttemptService.getRemainingLockTime(email);
            return new LoginResult(false,
                    "Account temporarily locked. Please try again in " + remainingTime + " seconds.",
                    null, remainingTime);
        }

        // Find applicant by email
        Optional<Applicant> applicantOpt = getAll().stream()
                .filter(a -> a.getContact() != null &&
                        a.getContact().getEmail().equalsIgnoreCase(email))
                .findFirst();

        if (applicantOpt.isPresent()) {
            Applicant applicant = applicantOpt.get();

            // Validate password
            if (validatePassword(rawPassword, applicant.getPassword())) {
                // Successful login - reset attempts
                loginAttemptService.loginSucceeded(email);
                return new LoginResult(true, "Login successful", applicant, 0);
            } else {
                // Failed login - record attempt
                loginAttemptService.loginFailed(email);
                int remainingAttempts = loginAttemptService.getRemainingAttempts(email);

                // Check if account is now locked
                if (loginAttemptService.isBlocked(email)) {
                    long remainingTime = loginAttemptService.getRemainingLockTime(email);
                    return new LoginResult(false,
                            "Account locked due to too many failed attempts. Please try again in " + remainingTime + " seconds.",
                            null, remainingTime);
                } else {
                    return new LoginResult(false,
                            "Incorrect password. " + remainingAttempts + " attempts remaining.",
                            null, remainingAttempts);
                }
            }
        } else {
            return new LoginResult(false, "Applicant not found.", null, 0);
        }
    }

    // Login Result inner class
    public static class LoginResult {
        private boolean success;
        private String message;
        private Applicant applicant;
        private long remainingLockTime;
        private int remainingAttempts;

        public LoginResult(boolean success, String message, Applicant applicant, long remainingLockTime) {
            this.success = success;
            this.message = message;
            this.applicant = applicant;
            this.remainingLockTime = remainingLockTime;
        }

        public LoginResult(boolean success, String message, Applicant applicant, int remainingAttempts) {
            this.success = success;
            this.message = message;
            this.applicant = applicant;
            this.remainingAttempts = remainingAttempts;
        }

        // Getters
        public boolean isSuccess() { return success; }
        public String getMessage() { return message; }
        public Applicant getApplicant() { return applicant; }
        public long getRemainingLockTime() { return remainingLockTime; }
        public int getRemainingAttempts() { return remainingAttempts; }
    }

}
