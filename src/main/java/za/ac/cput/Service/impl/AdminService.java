package za.ac.cput.Service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import za.ac.cput.Domain.Registrations.Vehicle;
import za.ac.cput.Domain.User.Admin;
import za.ac.cput.Domain.User.Applicant;
import za.ac.cput.Domain.bookings.Bookings;
import za.ac.cput.Domain.bookings.TestAppointment;
import za.ac.cput.Domain.bookings.VehicleDisc;
import za.ac.cput.Domain.payment.Payment;
import za.ac.cput.Domain.payment.Ticket;
import za.ac.cput.Domain.Registrations.Registration;
import za.ac.cput.Repository.*;
import za.ac.cput.Service.IAdminService;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService implements IAdminService {

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);

    @Autowired  // Add this annotation
    private AdminRepository adminRepository;
    private final ApplicantRepository applicantRepository;
    private final BookingsRepository bookingsRepository;
    private final PaymentRepository paymentRepository;
    private final RegistrationRepository registrationRepository;
    private final TestAppointmentRepository testAppointmentRepository;
    private final VehicleDiscRepository vehicleDiscRepository;

    private final TicketRepository ticketRepository;
    private final VehicleRepository vehicleRepository;
    @Autowired
    private LoginAttemptService loginAttemptService;

    @Autowired
    public AdminService(
            AdminRepository adminRepository,
            ApplicantRepository applicantRepository,
            BookingsRepository bookingsRepository,
            PaymentRepository paymentRepository,
            RegistrationRepository registrationRepository,
            TestAppointmentRepository testAppointmentRepository,
            VehicleDiscRepository vehicleDiscRepository,
            TicketRepository ticketRepository,
            VehicleRepository vehicleRepository) {
        this.adminRepository = adminRepository;
        this.applicantRepository = applicantRepository;
        this.bookingsRepository = bookingsRepository;
        this.paymentRepository = paymentRepository;
        this.registrationRepository = registrationRepository;
        this.testAppointmentRepository = testAppointmentRepository;
        this.vehicleDiscRepository = vehicleDiscRepository;
        this.ticketRepository = ticketRepository;
        this.vehicleRepository = vehicleRepository;
    }


    @Override
    public Admin create(Admin admin) {
        admin.setPassword(bCryptPasswordEncoder.encode(admin.getPassword()));
        return adminRepository.save(admin);
    }

    @Override
    public Admin read(Integer id) {
        return adminRepository.findById(id).orElse(null);
    }

    @Override
    public Admin update(Admin admin) {
        if (!adminRepository.existsById(admin.getUserId())) return null;
        return adminRepository.save(admin);
    }

    @Override
    public boolean deleteAdmin(Integer id) {
        if (adminRepository.existsById(id)) {
            adminRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }


    @Override
    public List<Applicant> getAllApplicants() {
        return applicantRepository.findAll();
    }

    @Override
    public boolean deleteApplicant(Integer id) {
        if (applicantRepository.existsById(id)) {
            applicantRepository.deleteById(id);
            return true;
        }
        return false;
    }


    @Override
    public List<Bookings> getBookings() {
        return bookingsRepository.findAll();
    }

    @Override
    public boolean deleteBooking(Long id) {
        if (bookingsRepository.existsById(id)) {
            bookingsRepository.deleteById(id);
            return true;
        }
        return false;
    }


    @Override
    public List<Payment> getPayments() {
        return paymentRepository.findAll();
    }

    @Override
    public boolean deletePayment(Integer id) {
        if (paymentRepository.existsById(id)) {
            paymentRepository.deleteById(id);
            return true;
        }
        return false;
    }


    @Override
    public List<Registration> getRegistration() {
        return registrationRepository.findAll();
    }

    @Override
    public List<TestAppointment> getTestAppointments() {
        return testAppointmentRepository.findAll();
    }

    @Override
    public boolean deleteTestAppointment(Long id) {
        if (testAppointmentRepository.existsById(id)) {
            testAppointmentRepository.deleteById(id);
            return true;
        }
        return false;
    }


    @Override
    public List<VehicleDisc> getVehicleDiscs() {
        return vehicleDiscRepository.findAll();
    }

    @Override
    public List<Vehicle> getVehicles() {
        return vehicleRepository.findAll();
    }

    @Override
    public boolean deleteVehicleDisc(Long id) {
        if (vehicleDiscRepository.existsById(id)) {
            vehicleDiscRepository.deleteById(id);
            return true;
        }
        return false;
    }


    @Override
    public List<Ticket> getTickets() {
        return ticketRepository.findAll();
    }

    @Override
    public boolean deleteTicket(Integer id) {
        if (ticketRepository.existsById(id)) {
            ticketRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Applicant updateApplicantStatus(Integer id, String statusStr, String reason) {
        Applicant applicant = applicantRepository.findById(id).orElse(null);
        if (applicant == null) return null;

        try {
            Applicant.Status status = Applicant.Status.valueOf(statusStr.toUpperCase());
            applicant.setStatus(status);
            applicant.setReason(reason);
            return applicantRepository.save(applicant);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status value. Use PENDING, ACCEPTED, or REJECTED.");
        }
    }

    @Override
    public TestAppointment updateTestResult(Long testAppointmentId, Boolean testResult, String notes) {
        // You need to inject TestAppointmentRepository in your AdminService
        TestAppointment testAppointment = testAppointmentRepository.findById(testAppointmentId)
                .orElseThrow(() -> new RuntimeException("Test appointment not found with ID: " + testAppointmentId));

        testAppointment.setTestResult(testResult);
        // If you have a notes field: testAppointment.setNotes(notes);

        return testAppointmentRepository.save(testAppointment);
    }

    public boolean validatePassword(String rawPassword, String encodedPassword) {
        return bCryptPasswordEncoder.matches(rawPassword, encodedPassword);
    }
    // Add this method to AdminService

    // Add this method to AdminService
    public LoginResult validateLogin(String email, String rawPassword) {
        System.out.println("🔐 VALIDATING ADMIN LOGIN for: " + email);

        // Check if account is blocked
        if (loginAttemptService.isBlocked(email)) {
            long remainingTime = loginAttemptService.getRemainingLockTime(email);
            String message = "Account temporarily locked. Please try again in " + remainingTime + " seconds.";
            System.out.println("❌ ADMIN LOGIN BLOCKED: " + message);
            return new LoginResult(false, message, null, remainingTime);
        }

        // Find admin by email
        Optional<Admin> adminOpt = getAllAdmins().stream()
                .filter(a -> a.getContact() != null &&
                        a.getContact().getEmail() != null &&
                        a.getContact().getEmail().equalsIgnoreCase(email))
                .findFirst();

        if (!adminOpt.isPresent()) {
            // Admin not found - count as failed attempt
            loginAttemptService.loginFailed(email);
            int remainingAttempts = loginAttemptService.getRemainingAttempts(email);
            String message = "Invalid email or password. " + remainingAttempts + " attempts remaining.";
            System.out.println("❌ ADMIN NOT FOUND: " + message);
            return new LoginResult(false, message, null, remainingAttempts);
        }

        Admin admin = adminOpt.get();

        // Validate password
        if (bCryptPasswordEncoder.matches(rawPassword, admin.getPassword())) {
            // ✅ SUCCESS: Reset attempts
            loginAttemptService.loginSucceeded(email);
            System.out.println("✅ ADMIN LOGIN SUCCESS: " + email);
            return new LoginResult(true, "Login successful", admin, 0);
        } else {
            // ❌ FAILED: Record attempt
            loginAttemptService.loginFailed(email);
            int remainingAttempts = loginAttemptService.getRemainingAttempts(email);

            // Check if account is now locked
            if (loginAttemptService.isBlocked(email)) {
                long remainingTime = loginAttemptService.getRemainingLockTime(email);
                String message = "Account locked due to too many failed attempts. Please try again in " + remainingTime + " seconds.";
                System.out.println("🔒 ADMIN ACCOUNT LOCKED: " + message);
                return new LoginResult(false, message, null, remainingTime);
            } else {
                String message = "Incorrect password. " + remainingAttempts + " attempts remaining.";
                System.out.println("❌ ADMIN INCORRECT PASSWORD: " + message);
                return new LoginResult(false, message, null, remainingAttempts);
            }
        }
    }

    // Add this inner class to AdminService
    public static class LoginResult {
        private boolean success;
        private String message;
        private Admin admin;
        private long remainingLockTime;
        private int remainingAttempts;

        public LoginResult(boolean success, String message, Admin admin, long remainingLockTime) {
            this.success = success;
            this.message = message;
            this.admin = admin;
            this.remainingLockTime = remainingLockTime;
            this.remainingAttempts = 0;
        }

        public LoginResult(boolean success, String message, Admin admin, int remainingAttempts) {
            this.success = success;
            this.message = message;
            this.admin = admin;
            this.remainingLockTime = 0;
            this.remainingAttempts = remainingAttempts;
        }

        // Getters
        public boolean isSuccess() { return success; }
        public String getMessage() { return message; }
        public Admin getAdmin() { return admin; }
        public long getRemainingLockTime() { return remainingLockTime; }
        public int getRemainingAttempts() { return remainingAttempts; }
    }

    // Add this method to AdminService class
    public boolean changePassword(Integer userId, String currentPassword, String newPassword) {
        try {
            System.out.println("🔐 ADMIN CHANGE PASSWORD: Attempting for user ID: " + userId);

            Admin admin = adminRepository.findById(userId).orElse(null);
            if (admin == null) {
                System.out.println("❌ ADMIN CHANGE PASSWORD: Admin not found with ID: " + userId);
                return false;
            }

            // Verify current password
            if (!bCryptPasswordEncoder.matches(currentPassword, admin.getPassword())) {
                System.out.println("❌ ADMIN CHANGE PASSWORD: Current password is incorrect");
                return false;
            }

            // Validate new password
            if (newPassword == null || newPassword.trim().isEmpty()) {
                System.out.println("❌ ADMIN CHANGE PASSWORD: New password cannot be empty");
                return false;
            }

            if (newPassword.length() < 6) {
                System.out.println("❌ ADMIN CHANGE PASSWORD: New password must be at least 6 characters");
                return false;
            }

            // Encode and set new password
            admin.setPassword(bCryptPasswordEncoder.encode(newPassword));
            adminRepository.save(admin);

            System.out.println("✅ ADMIN CHANGE PASSWORD: Password changed successfully for user ID: " + userId);
            return true;

        } catch (Exception e) {
            System.out.println("💥 ADMIN CHANGE PASSWORD ERROR: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    // Add these methods to AdminService class

    // Find admin by email using repository
    public Optional<Admin> findAdminByEmail(String email) {
        try {
            System.out.println("🔍 ADMIN SERVICE: Finding admin by email: " + email);
            return adminRepository.findByEmail(email);
        } catch (Exception e) {
            System.out.println("❌ ADMIN SERVICE: Error finding admin by email: " + e.getMessage());
            return Optional.empty();
        }
    }

    // Check if admin email exists
    public boolean adminEmailExists(String email) {
        try {
            System.out.println("🔍 ADMIN SERVICE: Checking if admin email exists: " + email);
            return adminRepository.existsByEmail(email);
        } catch (Exception e) {
            System.out.println("❌ ADMIN SERVICE: Error checking admin email: " + e.getMessage());
            return false;
        }
    }

    // Change password method using repository
    public boolean changeAdminPassword(String email, String currentPassword, String newPassword) {
        try {
            System.out.println("🔐 ADMIN CHANGE PASSWORD: Attempting for email: " + email);

            Optional<Admin> adminOpt = adminRepository.findByEmail(email);
            if (!adminOpt.isPresent()) {
                System.out.println("❌ ADMIN CHANGE PASSWORD: Admin not found with email: " + email);
                return false;
            }

            Admin admin = adminOpt.get();

            // Verify current password
            if (!bCryptPasswordEncoder.matches(currentPassword, admin.getPassword())) {
                System.out.println("❌ ADMIN CHANGE PASSWORD: Current password is incorrect");
                return false;
            }

            // Validate new password
            if (newPassword == null || newPassword.trim().isEmpty()) {
                System.out.println("❌ ADMIN CHANGE PASSWORD: New password cannot be empty");
                return false;
            }

            if (newPassword.length() < 6) {
                System.out.println("❌ ADMIN CHANGE PASSWORD: New password must be at least 6 characters");
                return false;
            }

            // Encode and set new password
            admin.setPassword(bCryptPasswordEncoder.encode(newPassword));
            adminRepository.save(admin);

            System.out.println("✅ ADMIN CHANGE PASSWORD: Password changed successfully for email: " + email);
            return true;

        } catch (Exception e) {
            System.out.println("💥 ADMIN CHANGE PASSWORD ERROR: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Change password without current password (for verified reset)
    public boolean resetAdminPassword(String email, String newPassword) {
        try {
            System.out.println("🔐 ADMIN RESET PASSWORD: Attempting for email: " + email);

            Optional<Admin> adminOpt = adminRepository.findByEmail(email);
            if (!adminOpt.isPresent()) {
                System.out.println("❌ ADMIN RESET PASSWORD: Admin not found with email: " + email);
                return false;
            }

            Admin admin = adminOpt.get();

            // Validate new password
            if (newPassword == null || newPassword.trim().isEmpty()) {
                System.out.println("❌ ADMIN RESET PASSWORD: New password cannot be empty");
                return false;
            }

            if (newPassword.length() < 6) {
                System.out.println("❌ ADMIN RESET PASSWORD: New password must be at least 6 characters");
                return false;
            }

            // Encode and set new password
            admin.setPassword(bCryptPasswordEncoder.encode(newPassword));
            adminRepository.save(admin);

            System.out.println("✅ ADMIN RESET PASSWORD: Password reset successfully for email: " + email);
            return true;

        } catch (Exception e) {
            System.out.println("💥 ADMIN RESET PASSWORD ERROR: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}

