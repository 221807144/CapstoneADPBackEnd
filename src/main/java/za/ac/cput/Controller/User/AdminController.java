package za.ac.cput.Controller.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.Domain.User.Admin;
import za.ac.cput.Domain.User.Applicant;
import za.ac.cput.Domain.bookings.TestAppointment;
import za.ac.cput.Service.impl.AdminService;
import za.ac.cput.Service.jwt.TokenUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/admins")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    // ✅ GET login page endpoint (public)
    @GetMapping("/login")
    public ResponseEntity<?> loginPage() {
        System.out.println("🔍 ADMIN LOGIN PAGE: Login page accessed");
        return ResponseEntity.ok(Map.of(
                "message", "Admin login page - use POST /admins/login with email and password to authenticate",
                "required_fields", "email, password",
                "email_domain", "@admin.co.za",
                "example", Map.of("email", "admin@admin.co.za", "password", "yourpassword")
        ));
    }

    // Create a new admin (public - no token required)
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody Admin admin) {
        if (admin.getContact() == null || admin.getContact().getEmail() == null) {
            return new ResponseEntity<>("Email is required", HttpStatus.BAD_REQUEST);
        }

        String email = admin.getContact().getEmail();

        // Admins must use admin email domain
        if (!email.endsWith("@admin.co.za")) {
            return new ResponseEntity<>("Admins must use @admin.co.za email domain", HttpStatus.BAD_REQUEST);
        }

        Admin created = adminService.create(admin);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // Read an admin by ID (protected - ADMIN only)
    @GetMapping("/read/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> read(@PathVariable Integer id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("👤 Admin accessing data: " + auth.getName());

        Admin admin = adminService.read(id);
        if (admin == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(admin);
    }

    // Update an admin (protected - ADMIN only)
    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update(@RequestBody Admin admin) {
        Admin updated = adminService.update(admin);
        if (updated == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(updated);
    }

    // Get all admins (protected - ADMIN only)
    @GetMapping("/getAll")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAll() {
        List<Admin> admins = adminService.getAllAdmins();
        return ResponseEntity.ok(admins);
    }

    // Login endpoint (public - no token required)
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, Object> loginRequest) {
        try {
            System.out.println("🔍 ADMIN LOGIN ATTEMPT: " + loginRequest);

            // Extract email and password from the request
            String email = null;
            String password = null;

            if (loginRequest.get("contact") instanceof Map) {
                Map<?, ?> contact = (Map<?, ?>) loginRequest.get("contact");
                email = (String) contact.get("email");
            } else if (loginRequest.get("email") != null) {
                email = (String) loginRequest.get("email");
            }

            password = (String) loginRequest.get("password");

            if (email == null || password == null) {
                System.out.println("❌ ADMIN LOGIN: Missing email or password");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of(
                                "success", false,
                                "message", "Email and password are required",
                                "status", "error"
                        ));
            }

            System.out.println("🔐 ADMIN LOGIN: Validating credentials for: " + email);

            AdminService.LoginResult result = adminService.validateLogin(email, password);

            if (result.isSuccess()) {
                Admin admin = result.getAdmin();

                // Generate token using TokenUtil
                String token = tokenUtil.generateToken(
                        admin.getContact().getEmail(),
                        "ROLE_ADMIN",
                        admin.getUserId(),
                        "ADMIN"
                );

                // Create response with token and user data
                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("token", token);
                response.put("user", admin);
                response.put("role", "ROLE_ADMIN");
                response.put("message", "Login successful");
                response.put("status", "success");

                System.out.println("✅ ADMIN LOGIN SUCCESS: Token generated for: " + admin.getContact().getEmail());
                return ResponseEntity.ok(response);
            } else {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", result.getMessage());
                response.put("status", "error");

                if (result.getRemainingLockTime() > 0) {
                    response.put("locked", true);
                    response.put("remainingLockTime", result.getRemainingLockTime());
                } else {
                    response.put("remainingAttempts", result.getRemainingAttempts());
                }

                System.out.println("❌ ADMIN LOGIN FAILED: " + response);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
        } catch (Exception e) {
            System.out.println("💥 ADMIN LOGIN ERROR: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "success", false,
                            "message", "Login failed: " + e.getMessage(),
                            "status", "error"
                    ));
        }
    }

    // Admin dashboard data (protected - ADMIN only)
    @GetMapping("/all-data")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllData() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("📊 Admin dashboard accessed by: " + auth.getName());

        Map<String, Object> allData = new HashMap<>();
        allData.put("applicants", adminService.getAllApplicants());
        allData.put("admins", adminService.getAllAdmins());
        allData.put("bookings", adminService.getBookings());
        allData.put("payments", adminService.getPayments());
        allData.put("registrations", adminService.getRegistration());
        allData.put("testAppointments", adminService.getTestAppointments());
        allData.put("vehicleDiscs", adminService.getVehicleDiscs());
        allData.put("vehicles", adminService.getVehicles());
        allData.put("tickets", adminService.getTickets());

        return ResponseEntity.ok(allData);
    }

    // Update applicant status (protected - ADMIN only)
    @PutMapping("/update-status/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateApplicantStatus(@PathVariable Integer id,
                                                   @RequestBody Map<String, String> request) {
        String status = request.get("status");
        String reason = request.get("reason");

        if (status == null) {
            return new ResponseEntity<>("Status is required", HttpStatus.BAD_REQUEST);
        }

        try {
            Applicant updated = adminService.updateApplicantStatus(id, status, reason);
            if (updated != null) {
                return ResponseEntity.ok(updated);
            } else {
                return new ResponseEntity<>("Applicant not found", HttpStatus.NOT_FOUND);
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Update test result (protected - ADMIN only)
    @PutMapping("/test-appointments/update-result/{testAppointmentId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateTestResult(@PathVariable Long testAppointmentId,
                                              @RequestBody Map<String, Object> request) {
        Boolean testResult = (Boolean) request.get("testResult");
        String notes = (String) request.get("notes");

        if (testResult == null) {
            return new ResponseEntity<>("Test result is required", HttpStatus.BAD_REQUEST);
        }

        try {
            TestAppointment updated = adminService.updateTestResult(testAppointmentId, testResult, notes);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // Delete operations (protected - ADMIN only)
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteAdmin(@PathVariable Integer id) {
        boolean deleted = adminService.deleteAdmin(id);
        if (deleted) {
            return ResponseEntity.ok("Admin deleted successfully");
        }
        return new ResponseEntity<>("Admin not found", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/applicants/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteApplicant(@PathVariable Integer id) {
        boolean deleted = adminService.deleteApplicant(id);
        if (deleted) {
            return ResponseEntity.ok("Applicant deleted successfully");
        }
        return new ResponseEntity<>("Applicant not found", HttpStatus.NOT_FOUND);
    }

    // Check roles endpoint
    @GetMapping("/check-roles")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> checkRoles() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Map<String, Object> response = new HashMap<>();
        response.put("username", auth.getName());
        response.put("authorities", auth.getAuthorities().toString());
        response.put("isAuthenticated", auth.isAuthenticated());
        response.put("hasRoleApplicant", auth.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_APPLICANT")));
        response.put("hasRoleAdmin", auth.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN")));

        System.out.println("🔐 ADMIN ROLE CHECK: " + response);
        return ResponseEntity.ok(response);
    }

    // Validate token endpoint (public)
    @GetMapping("/validate-token")
    public ResponseEntity<?> validateToken(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("valid", false, "message", "Authorization header missing or invalid"));
            }

            String token = authHeader.substring(7);
            if (tokenUtil.validateToken(token)) {
                Map<String, Object> response = new HashMap<>();
                response.put("valid", true);
                response.put("username", tokenUtil.extractUsername(token));
                response.put("role", tokenUtil.extractRole(token));
                response.put("userId", tokenUtil.extractUserId(token));
                response.put("userType", tokenUtil.extractUserType(token));
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("valid", false, "message", "Invalid token"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("valid", false, "message", "Error validating token: " + e.getMessage()));
        }
    }

    // Test Token endpoint (public)
    @GetMapping("/test-token")
    public ResponseEntity<?> testToken() {
        try {
            String testToken = tokenUtil.generateToken("test@admin.co.za", "ROLE_ADMIN", 999, "ADMIN");

            Map<String, Object> response = new HashMap<>();
            response.put("token", testToken);
            response.put("valid", tokenUtil.validateToken(testToken));
            response.put("username", tokenUtil.extractUsername(testToken));
            response.put("role", tokenUtil.extractRole(testToken));
            response.put("userId", tokenUtil.extractUserId(testToken));
            response.put("userType", tokenUtil.extractUserType(testToken));

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Token test failed: " + e.getMessage()));
        }
    }
}