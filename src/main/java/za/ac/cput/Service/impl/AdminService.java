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

    private BCryptPasswordEncoder bCryptPasswordEncoder =  new BCryptPasswordEncoder(12);


    private final AdminRepository adminRepository;
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
    public LoginResult validateLogin(String email, String rawPassword) {
        // Check if account is blocked
        if (loginAttemptService.isBlocked(email)) {
            long remainingTime = loginAttemptService.getRemainingLockTime(email);
            return new LoginResult(false,
                    "Account temporarily locked. Please try again in " + remainingTime + " seconds.",
                    null, remainingTime);
        }

        // Find admin by email
        Optional<Admin> adminOpt = getAllAdmins().stream()
                .filter(a -> a.getContact() != null &&
                        a.getContact().getEmail().equalsIgnoreCase(email))
                .findFirst();

        if (adminOpt.isPresent()) {
            Admin admin = adminOpt.get();

            // Validate password
            if (bCryptPasswordEncoder.matches(rawPassword, admin.getPassword())) {
                // Successful login - reset attempts
                loginAttemptService.loginSucceeded(email);
                return new LoginResult(true, "Login successful", admin, 0);
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
            return new LoginResult(false, "Admin not found.", null, 0);
        }
    }

    // Login Result inner class for Admin
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
        }

        public LoginResult(boolean success, String message, Admin admin, int remainingAttempts) {
            this.success = success;
            this.message = message;
            this.admin = admin;
            this.remainingAttempts = remainingAttempts;
        }

        // Getters
        public boolean isSuccess() { return success; }
        public String getMessage() { return message; }
        public Admin getAdmin() { return admin; }
        public long getRemainingLockTime() { return remainingLockTime; }
        public int getRemainingAttempts() { return remainingAttempts; }
    }



}