package za.ac.cput.Controller.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.Domain.User.Applicant;
import za.ac.cput.Service.impl.ApplicantService;
import za.ac.cput.Service.jwt.TokenUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/applicants")
public class ApplicantController {

    private final ApplicantService applicantService;

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    public ApplicantController(ApplicantService applicantService) {
        this.applicantService = applicantService;
    }

    // ✅ Public test endpoint
    @GetMapping("/test-public")
    public ResponseEntity<?> testPublic() {
        System.out.println("✅ TEST PUBLIC: This works without login");
        return ResponseEntity.ok(Map.of("message", "Public endpoint works!"));
    }

    // ✅ GET login page endpoint (public)
    @GetMapping("/login")
    public ResponseEntity<?> loginPage() {
        System.out.println("🔍 APPLICANT LOGIN PAGE: Login page accessed");
        return ResponseEntity.ok(Map.of(
                "message", "Applicant login page - use POST /applicants/login with email and password to authenticate",
                "required_fields", "email, password",
                "example", Map.of("email", "user@example.com", "password", "yourpassword")
        ));
    }

    // ✅ Protected test endpoint - FIXED: Added @PreAuthorize
    @GetMapping("/test-protected")
    @PreAuthorize("hasAnyRole('APPLICANT', 'ADMIN')")
    public ResponseEntity<?> testProtected() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("🛡️ TEST PROTECTED: User " + auth.getName() + " with roles: " + auth.getAuthorities());

        return ResponseEntity.ok(Map.of(
                "message", "Protected endpoint works!",
                "user", auth.getName(),
                "roles", auth.getAuthorities().toString(),
                "authenticated", auth.isAuthenticated()
        ));
    }

    // ✅ Check roles endpoint - FIXED: Added @PreAuthorize
    @GetMapping("/check-roles")
    @PreAuthorize("hasAnyRole('APPLICANT', 'ADMIN')")
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

        System.out.println("🔐 ROLE CHECK: " + response);
        return ResponseEntity.ok(response);
    }

    // Create a new applicant (public - no token required)
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody Applicant applicant) {
        if (applicant.getContact() == null || applicant.getContact().getEmail() == null) {
            return new ResponseEntity<>("Email is required", HttpStatus.BAD_REQUEST);
        }

        String email = applicant.getContact().getEmail();

        if (email.endsWith("@admin.co.za")) {
            return new ResponseEntity<>("Applicants cannot use admin email domain (@admin.co.za)", HttpStatus.BAD_REQUEST);
        }

        if (!email.contains("@")) {
            return new ResponseEntity<>("Invalid email format for applicant", HttpStatus.BAD_REQUEST);
        }

        Applicant created = applicantService.create(applicant);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // Read an applicant by ID (protected)
    @GetMapping("/read/{id}")
    @PreAuthorize("hasAnyRole('APPLICANT', 'ADMIN')")
    public ResponseEntity<?> read(@PathVariable Integer id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("👤 Accessing applicant data by user: " + auth.getName());

        Applicant applicant = applicantService.read(id);
        if (applicant == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(applicant);
    }

    // ✅ FIXED: Update an applicant - SIMPLIFIED (no path variable)
    @PutMapping("/update")
    @PreAuthorize("hasAnyRole('APPLICANT', 'ADMIN')")
    public ResponseEntity<?> update(@RequestBody Applicant applicant) {
        System.out.println("🔧 UPDATE APPLICANT: Updating user ID: " + applicant.getUserId());

        Applicant updated = applicantService.update(applicant);
        if (updated == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(updated);
    }

    // Get all applicants (protected - ADMIN only)
    @GetMapping("/getAll")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAll() {
        List<Applicant> applicants = applicantService.getAll();
        return ResponseEntity.ok(applicants);
    }

    // Login endpoint (public - no token required)
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, Object> loginRequest) {
        try {
            System.out.println("🔍 APPLICANT LOGIN ATTEMPT: " + loginRequest);

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
                System.out.println("❌ APPLICANT LOGIN: Missing email or password");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of(
                                "success", false,
                                "message", "Email and password are required",
                                "status", "error"
                        ));
            }

            System.out.println("🔐 APPLICANT LOGIN: Validating credentials for: " + email);

            ApplicantService.LoginResult result = applicantService.validateLogin(email, password);

            if (result.isSuccess()) {
                Applicant applicant = result.getApplicant();

                // Generate token using TokenUtil
                String token = tokenUtil.generateToken(
                        applicant.getContact().getEmail(),
                        "ROLE_APPLICANT",
                        applicant.getUserId(),
                        "APPLICANT"
                );

                // Create response with token and user data
                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("token", token);
                response.put("user", applicant);
                response.put("role", "ROLE_APPLICANT");
                response.put("message", "Login successful");
                response.put("status", "success");

                System.out.println("✅ APPLICANT LOGIN SUCCESS: Token generated for: " + applicant.getContact().getEmail());
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

                System.out.println("❌ APPLICANT LOGIN FAILED: " + response);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
        } catch (Exception e) {
            System.out.println("💥 APPLICANT LOGIN ERROR: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "success", false,
                            "message", "Login failed: " + e.getMessage(),
                            "status", "error"
                    ));
        }
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
            String testToken = tokenUtil.generateToken("test@applicant.com", "ROLE_APPLICANT", 888, "APPLICANT");

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

    // Get applicant by ID (alternative endpoint)
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('APPLICANT', 'ADMIN')")
    public ResponseEntity<?> getApplicantById(@PathVariable Integer id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("👤 Getting applicant by ID: " + id + " requested by: " + auth.getName());

        Applicant applicant = applicantService.read(id);
        if (applicant == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(applicant);
    }

    // Delete applicant (ADMIN only)
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        boolean deleted = applicantService.delete(id);
        if (deleted) {
            return ResponseEntity.ok(Map.of("message", "Applicant deleted successfully"));
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}