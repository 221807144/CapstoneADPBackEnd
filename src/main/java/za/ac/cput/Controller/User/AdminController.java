package za.ac.cput.Controller.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.Domain.Registrations.Registration;
import za.ac.cput.Domain.Registrations.Vehicle;
import za.ac.cput.Domain.User.Admin;
import za.ac.cput.Domain.User.Applicant;
import za.ac.cput.Domain.bookings.Bookings;
import za.ac.cput.Domain.bookings.TestAppointment;
import za.ac.cput.Domain.bookings.VehicleDisc;
import za.ac.cput.Domain.payment.Payment;
import za.ac.cput.Domain.payment.Ticket;
import za.ac.cput.Service.impl.AdminService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/admins")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    // Create Admin
    @PostMapping("/create")
    public ResponseEntity<Admin> create(@RequestBody Admin admin) {
        return ResponseEntity.ok(adminService.create(admin));
    }

    // Read Admin by ID
    @GetMapping("/read/{id}")
    public ResponseEntity<Admin> read(@PathVariable Integer id) {
        Admin admin = adminService.read(id);
        if (admin == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(admin);
    }

    // Update Admin
    @PutMapping("/update")
    public ResponseEntity<Admin> update(@RequestBody Admin admin) {
        Admin updated = adminService.update(admin);
        if (updated == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updated);
    }

    // Delete Admin
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (!adminService.deleteAdmin(id))
            return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }

    // Get all data for dashboard
    @GetMapping("/all-data")
    public ResponseEntity<Map<String, Object>> getAllData() {
        Map<String, Object> data = new HashMap<>();
        data.put("admins", adminService.getAllAdmins());
        data.put("applicants", adminService.getAllApplicants());
        data.put("bookings", adminService.getBookings());
        data.put("payments", adminService.getPayments());
        data.put("registrations", adminService.getRegistration());
        data.put("testAppointments", adminService.getTestAppointments());
        data.put("vehicleDiscs", adminService.getVehicleDiscs());
        data.put("vehicles", adminService.getVehicles());
        data.put("tickets", adminService.getTickets());
        return ResponseEntity.ok(data);
    }

    // Admin login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Admin loginRequest) {
        if (loginRequest.getContact() == null || loginRequest.getContact().getEmail() == null) {
            return ResponseEntity.badRequest().body("Email is required");
        }
        if (loginRequest.getPassword() == null) {
            return ResponseEntity.badRequest().body("Password is required");
        }

        return adminService.getAllAdmins().stream()
                .filter(a -> a.getContact() != null &&
                        a.getContact().getEmail().equalsIgnoreCase(loginRequest.getContact().getEmail()))
                .findFirst()
                .map(admin -> {
                    if (loginRequest.getPassword().equals(admin.getPassword())) {
                        Map<String, Object> response = new HashMap<>();
                        response.put("message", "Login successful!");
                        response.put("userId", admin.getUserId());
                        response.put("firstName", admin.getFirstName());
                        response.put("lastName", admin.getLastName());
                        return ResponseEntity.ok(response);
                    } else {
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect password.");
                    }
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Admin not found."));
    }

    @PutMapping("/update-status/{id}")
    public ResponseEntity<?> updateApplicantStatus(@PathVariable Integer id, @RequestBody Map<String, String> payload) {
        String statusStr = payload.get("status");
        String reason = payload.get("reason");

        if (statusStr == null) return ResponseEntity.badRequest().body("Status is required");

        try {
            Applicant updated = adminService.updateApplicantStatus(id, statusStr, reason);
            if (updated == null) return ResponseEntity.notFound().build();
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/payments")
    public ResponseEntity<List<Payment>> getAllPayments() {
        List<Payment> payments = adminService.getPayments();
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/tickets")
    public ResponseEntity<List<Ticket>> getAllTickets() {
        List<Ticket> tickets = adminService.getTickets();
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/vehicles")
    public ResponseEntity<List<Vehicle>> getAllVehicles() {
        List<Vehicle> vehicles = adminService.getVehicles();
        return ResponseEntity.ok(vehicles);
    }

    @GetMapping("/vehicle-discs")
    public ResponseEntity<List<VehicleDisc>> getAllVehicleDiscs() {
        List<VehicleDisc> vehicleDiscs = adminService.getVehicleDiscs();
        return ResponseEntity.ok(vehicleDiscs);
    }

    @GetMapping("/bookings")
    public ResponseEntity<List<Bookings>> getAllBookings() {
        List<Bookings> bookings = adminService.getBookings();
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/registrations")
    public ResponseEntity<List<Registration>> getAllRegistrations() {
        List<Registration> registrations = adminService.getRegistration();
        return ResponseEntity.ok(registrations);
    }



    @DeleteMapping("/applicants/delete/{id}")
    public ResponseEntity<Void> deleteApplicant(@PathVariable Integer id) {
        if (!adminService.deleteApplicant(id))
            return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/bookings/delete/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
        if (!adminService.deleteBooking(id))
            return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }

    // Delete Payment
    @DeleteMapping("/payments/delete/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Integer id) {
        if (!adminService.deletePayment(id))
            return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }

    // Delete Test Appointment
    @DeleteMapping("/test-appointments/delete/{id}")
    public ResponseEntity<Void> deleteTestAppointment(@PathVariable Long id) {
        if (!adminService.deleteTestAppointment(id))
            return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }

    // Delete Vehicle Disc
    @DeleteMapping("/vehicle-discs/delete/{id}")
    public ResponseEntity<Void> deleteVehicleDisc(@PathVariable Long id) {
        if (!adminService.deleteVehicleDisc(id))
            return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }

    // Delete Ticket
    @DeleteMapping("/tickets/delete/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Integer id) {
        if (!adminService.deleteTicket(id))
            return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }
}