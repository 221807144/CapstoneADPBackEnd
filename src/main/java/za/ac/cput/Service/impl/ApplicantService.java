package za.ac.cput.Service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import za.ac.cput.Domain.User.Applicant;
import za.ac.cput.Domain.User.Learners;
import za.ac.cput.Domain.User.License;
import za.ac.cput.Repository.ApplicantRepository;
import za.ac.cput.Repository.LearnersRepository;
import za.ac.cput.Repository.LicenseRepository;
import za.ac.cput.Service.IApplicantService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@Service
public class ApplicantService implements IApplicantService {

    @Autowired
    private LoginAttemptService loginAttemptService;

    //    private final ApplicantRepository applicantRepository;
   private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);


    private final ApplicantRepository applicantRepository;
    private final LicenseRepository licenseRepository; // Add this
    private final LearnersRepository learnersRepository; // Add this

    @Autowired
    public ApplicantService(ApplicantRepository applicantRepository,
                            LicenseRepository licenseRepository,
                            LearnersRepository learnersRepository) {
        this.applicantRepository = applicantRepository;
        this.licenseRepository = licenseRepository;
        this.learnersRepository = learnersRepository;
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
    public Applicant saveOrUpdateDocuments(Integer applicantId, Map<String, Object> docData) {
        Optional<Applicant> applicantOpt = applicantRepository.findById(applicantId);
        if (!applicantOpt.isPresent()) {
            throw new RuntimeException("Applicant not found");
        }

        Applicant existingApplicant = applicantOpt.get();
        System.out.println("🔍 DEEP DEBUG - Existing applicant:");
        System.out.println("  - ID: " + existingApplicant.getUserId());
        System.out.println("  - Contact Object: " + existingApplicant.getContact());
        System.out.println("  - Contact ID: " + (existingApplicant.getContact() != null ? existingApplicant.getContact().getContactId() : "NULL"));
        System.out.println("  - Contact Email: " + (existingApplicant.getContact() != null ? existingApplicant.getContact().getEmail() : "NULL"));
        System.out.println("  - Birth Date: " + existingApplicant.getBirthDate());

        // Test the Builder copy
        Applicant testCopy = new Applicant.Builder().copy(existingApplicant).build();
        System.out.println("🔍 DEEP DEBUG - After Builder copy:");
        System.out.println("  - Contact Object: " + testCopy.getContact());
        System.out.println("  - Contact ID: " + (testCopy.getContact() != null ? testCopy.getContact().getContactId() : "NULL"));
        System.out.println("  - Birth Date: " + testCopy.getBirthDate());
        String type = (String) docData.get("type");
        String code = (String) docData.get("code");
        LocalDate issueDate = LocalDate.parse((String) docData.get("issueDate"));
        LocalDate expiryDate = LocalDate.parse((String) docData.get("expiryDate"));

        if ("LICENSE".equalsIgnoreCase(type)) {
            return updateOrCreateLicense(existingApplicant, code, issueDate, expiryDate);
        } else if ("LEARNERS".equalsIgnoreCase(type)) {
            return updateOrCreateLearners(existingApplicant, code, issueDate, expiryDate);
        } else {
            throw new RuntimeException("Unknown document type: " + type);
        }
    }

    private Applicant updateOrCreateLicense(Applicant applicant, String code, LocalDate issueDate, LocalDate expiryDate) {
        System.out.println("💾 Updating/Creating license for applicant: " + applicant.getUserId());

        License license;
        if (applicant.getLicense() != null) {
            // Update existing license using Builder
            license = new License.Builder()
                    .setLicenseId(applicant.getLicense().getLicenseId())
                    .setLicenseCode(code)
                    .setIssueDate(issueDate)
                    .setExpiryDate(expiryDate)
                    .build();
        } else {
            // Create new license
            license = new License.Builder()
                    .setLicenseCode(code)
                    .setIssueDate(issueDate)
                    .setExpiryDate(expiryDate)
                    .build();
        }

        License savedLicense = licenseRepository.save(license);

        // Update applicant using Builder
        Applicant updatedApplicant = new Applicant.Builder()
                .copy(applicant)
                .setLicense(savedLicense)
                .build();

        return applicantRepository.save(updatedApplicant);
    }

    private Applicant updateOrCreateLearners(Applicant applicant, String code, LocalDate issueDate, LocalDate expiryDate) {
        System.out.println("💾 Updating/Creating learners for applicant: " + applicant.getUserId());

        Learners learners;
        if (applicant.getLearners() != null) {
            // Update existing learners using Builder
            learners = new Learners.Builder()
                    .setLearnersId(applicant.getLearners().getLearnersId())
                    .setLearnersCode(code)
                    .setIssueDate(issueDate)
                    .setExpiryDate(expiryDate)
                    .build();
        } else {
            // Create new learners
            learners = new Learners.Builder()
                    .setLearnersCode(code)
                    .setIssueDate(issueDate)
                    .setExpiryDate(expiryDate)
                    .build();
        }

        Learners savedLearners = learnersRepository.save(learners);

        // Update applicant using Builder
        Applicant updatedApplicant = new Applicant.Builder()
                .copy(applicant)
                .setLearners(savedLearners)
                .build();

        return applicantRepository.save(updatedApplicant);
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

    // Add these methods to ApplicantService class

    // Find applicant by email using repository
    public Optional<Applicant> findApplicantByEmail(String email) {
        try {
            System.out.println("🔍 APPLICANT SERVICE: Finding applicant by email: " + email);
            return applicantRepository.findByEmail(email);
        } catch (Exception e) {
            System.out.println("❌ APPLICANT SERVICE: Error finding applicant by email: " + e.getMessage());
            return Optional.empty();
        }
    }

    // Check if applicant email exists
    public boolean applicantEmailExists(String email) {
        try {
            System.out.println("🔍 APPLICANT SERVICE: Checking if applicant email exists: " + email);
            return applicantRepository.existsByEmail(email);
        } catch (Exception e) {
            System.out.println("❌ APPLICANT SERVICE: Error checking applicant email: " + e.getMessage());
            return false;
        }
    }

    // ✅ ADD THIS MISSING METHOD - Verify email for password reset
    public Map<String, Object> verifyApplicantEmailForPasswordReset(String email) {
        try {
            System.out.println("🔍 APPLICANT SERVICE: Verifying email for password reset: " + email);

            boolean emailExists = applicantRepository.existsByEmail(email);

            if (!emailExists) {
                return Map.of(
                        "success", false,
                        "message", "Applicant email not found in our system"
                );
            }

            // Generate verification code (in real app, send via email)
            String verificationCode = generateVerificationCode();
            System.out.println("📧 Verification code for applicant " + email + ": " + verificationCode);

            return Map.of(
                    "success", true,
                    "message", "Verification code sent to your email",
                    "demoCode", verificationCode // Remove in production
            );

        } catch (Exception e) {
            System.out.println("❌ APPLICANT SERVICE: Error verifying email: " + e.getMessage());
            return Map.of(
                    "success", false,
                    "message", "Error verifying email: " + e.getMessage()
            );
        }
    }

    // ✅ ADD THIS MISSING METHOD - Reset applicant password with verification
    public Map<String, Object> resetApplicantPassword(String email, String newPassword) {
        try {
            System.out.println("🔐 APPLICANT SERVICE: Resetting password for: " + email);

            Optional<Applicant> applicantOpt = applicantRepository.findByEmail(email);
            if (!applicantOpt.isPresent()) {
                return Map.of(
                        "success", false,
                        "message", "Applicant not found with email: " + email
                );
            }

            Applicant applicant = applicantOpt.get();

            // Validate new password
            if (newPassword == null || newPassword.trim().isEmpty()) {
                return Map.of(
                        "success", false,
                        "message", "New password cannot be empty"
                );
            }

            if (newPassword.length() < 6) {
                return Map.of(
                        "success", false,
                        "message", "New password must be at least 6 characters long"
                );
            }

            // Encode and set new password
            applicant.setPassword(bCryptPasswordEncoder.encode(newPassword));
            applicantRepository.save(applicant);

            System.out.println("✅ APPLICANT SERVICE: Password reset successfully for: " + email);
            return Map.of(
                    "success", true,
                    "message", "Password reset successfully"
            );

        } catch (Exception e) {
            System.out.println("❌ APPLICANT SERVICE: Error resetting password: " + e.getMessage());
            return Map.of(
                    "success", false,
                    "message", "Error resetting password: " + e.getMessage()
            );
        }
    }

    // Change password method using repository (for logged-in users)
    public boolean changeApplicantPassword(String email, String currentPassword, String newPassword) {
        try {
            System.out.println("🔐 APPLICANT CHANGE PASSWORD: Attempting for email: " + email);

            Optional<Applicant> applicantOpt = applicantRepository.findByEmail(email);
            if (!applicantOpt.isPresent()) {
                System.out.println("❌ APPLICANT CHANGE PASSWORD: Applicant not found with email: " + email);
                return false;
            }

            Applicant applicant = applicantOpt.get();

            // Verify current password
            if (!bCryptPasswordEncoder.matches(currentPassword, applicant.getPassword())) {
                System.out.println("❌ APPLICANT CHANGE PASSWORD: Current password is incorrect");
                return false;
            }

            // Validate new password
            if (newPassword == null || newPassword.trim().isEmpty()) {
                System.out.println("❌ APPLICANT CHANGE PASSWORD: New password cannot be empty");
                return false;
            }

            if (newPassword.length() < 6) {
                System.out.println("❌ APPLICANT CHANGE PASSWORD: New password must be at least 6 characters");
                return false;
            }

            // Encode and set new password
            applicant.setPassword(bCryptPasswordEncoder.encode(newPassword));
            applicantRepository.save(applicant);

            System.out.println("✅ APPLICANT CHANGE PASSWORD: Password changed successfully for email: " + email);
            return true;

        } catch (Exception e) {
            System.out.println("💥 APPLICANT CHANGE PASSWORD ERROR: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Helper method to generate verification code
    private String generateVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }


}