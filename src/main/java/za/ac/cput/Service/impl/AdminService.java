package za.ac.cput.Service.impl;

import org.springframework.beans.factory.annotation.Autowired;
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

@Service
public class AdminService implements IAdminService {

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

    // --- Admin CRUD ---
    @Override
    public Admin create(Admin admin) {
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

    // --- Applicant ---
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

    // --- Bookings ---
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

    // --- Payments ---
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

    // --- Registrations ---
    @Override
    public List<Registration> getRegistration() {
        return registrationRepository.findAll();
    }

    // --- Test Appointments ---
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

    // --- Vehicle Discs ---
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

    // --- Tickets ---
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
}