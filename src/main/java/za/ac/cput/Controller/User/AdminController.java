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

    // Create Admin   made changes to create
//    @PostMapping("/create")
//    public ResponseEntity<Admin> create(@RequestBody Admin admin) {
//        return ResponseEntity.ok(adminService.create(admin));
//    }

    // Create Admin with strict email validation
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody Admin admin) {
        // Validate admin email - MUST end with @admin.co.za
        if (admin.getContact() == null || admin.getContact().getEmail() == null) {
            return ResponseEntity.badRequest().body("Email is required for admin registration");
        }

        String email = admin.getContact().getEmail();

        // Strict validation: admin emails must end with @admin.co.za
        if (!email.endsWith("@admin.co.za")) {
            return ResponseEntity.badRequest()
                    .body("Admin registration requires an email ending with @admin.co.za");
        }

        Admin created = adminService.create(admin);
        return ResponseEntity.ok(created);
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


// Admin login with attempt tracking
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Admin loginRequest) {
        if (loginRequest.getContact() == null || loginRequest.getContact().getEmail() == null) {
            return ResponseEntity.badRequest().body("Email is required");
        }
        if (loginRequest.getPassword() == null) {
            return ResponseEntity.badRequest().body("Password is required");
        }

        String email = loginRequest.getContact().getEmail();
        AdminService.LoginResult result = adminService.validateLogin(email, loginRequest.getPassword());

        Map<String, Object> response = new HashMap<>();
        response.put("success", result.isSuccess());
        response.put("message", result.getMessage());

        if (result.isSuccess()) {
            response.put("admin", result.getAdmin());
            // Include admin details in response
            Map<String, Object> adminInfo = new HashMap<>();
            adminInfo.put("userId", result.getAdmin().getUserId());
            adminInfo.put("firstName", result.getAdmin().getFirstName());
            adminInfo.put("lastName", result.getAdmin().getLastName());
            adminInfo.put("role", result.getAdmin().getRole());
            response.put("adminInfo", adminInfo);
            return ResponseEntity.ok(response);
        } else {
            // Add attempt tracking information
            if (result.getRemainingLockTime() > 0) {
                response.put("locked", true);
                response.put("remainingLockTime", result.getRemainingLockTime());
            } else {
                response.put("remainingAttempts", result.getRemainingAttempts());
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
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
    // Update Test Result
    @PutMapping("/test-appointments/update-result/{id}")
    public ResponseEntity<?> updateTestResult(
            @PathVariable Long id,
            @RequestBody Map<String, Object> payload) {

        Boolean testResult = (Boolean) payload.get("testResult");
        String notes = (String) payload.get("notes");

        if (testResult == null) {
            return ResponseEntity.badRequest().body("Test result is required");
        }

        try {
            TestAppointment updated = adminService.updateTestResult(id, testResult, notes);
            if (updated == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating test result: " + e.getMessage());
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