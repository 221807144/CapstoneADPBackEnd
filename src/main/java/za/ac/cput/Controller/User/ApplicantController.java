package za.ac.cput.Controller.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.Domain.User.Applicant;
import za.ac.cput.Service.impl.ApplicantService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000") // allow frontend calls
@RestController
@RequestMapping("/applicants")
public class ApplicantController {

    private final ApplicantService applicantService;

    @Autowired
    public ApplicantController(ApplicantService applicantService) {
        this.applicantService = applicantService;
    }

//    // Create a new applicant
//    @PostMapping("/create")
//    public ResponseEntity<Applicant> create(@RequestBody Applicant applicant) { //updated
//        Applicant created = applicantService.create(applicant);
//        return new ResponseEntity<>(created, HttpStatus.CREATED);
//    }

    // Create a new applicant with email validation
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody Applicant applicant) {
        // Validate email format for applicant - must contain @ but NOT end with @admin.co.za
        if (applicant.getContact() == null || applicant.getContact().getEmail() == null) {
            return new ResponseEntity<>("Email is required", HttpStatus.BAD_REQUEST);
        }

        String email = applicant.getContact().getEmail();

        // Applicants cannot use admin email domain
        if (email.endsWith("@admin.co.za")) {
            return new ResponseEntity<>("Applicants cannot use admin email domain (@admin.co.za)", HttpStatus.BAD_REQUEST);
        }

        // Basic email validation - must contain @
        if (!email.contains("@")) {
            return new ResponseEntity<>("Invalid email format for applicant", HttpStatus.BAD_REQUEST);
        }

        Applicant created = applicantService.create(applicant);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }


    // Read an applicant by ID
    @GetMapping("/read/{id}")
    public ResponseEntity<Applicant> read(@PathVariable Integer id) {
        Applicant applicant = applicantService.read(id);
        if (applicant == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(applicant);
    }

    // Update an applicant
    @PutMapping("/update")
    public ResponseEntity<Applicant> update(@RequestBody Applicant applicant) {
        Applicant updated = applicantService.update(applicant);
        if (updated == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(updated);
    }

    // Get all applicants
    @GetMapping("/getAll")
    public ResponseEntity<List<Applicant>> getAll() {
        List<Applicant> applicants = applicantService.getAll();
        return ResponseEntity.ok(applicants);
    }

    // Login endpoint: returns full applicant object
// Login endpoint with attempt tracking but same successful response format
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Applicant loginRequest) {
        if (loginRequest.getContact() == null || loginRequest.getContact().getEmail() == null) {
            return new ResponseEntity<>("Email is required", HttpStatus.BAD_REQUEST);
        }
        if (loginRequest.getPassword() == null) {
            return new ResponseEntity<>("Password is required", HttpStatus.BAD_REQUEST);
        }

        String email = loginRequest.getContact().getEmail();
        ApplicantService.LoginResult result = applicantService.validateLogin(email, loginRequest.getPassword());

        if (result.isSuccess()) {
            // Return the same format as before - just the applicant object directly
            return ResponseEntity.ok(result.getApplicant());
        } else {
            // For failed logins, return the attempt tracking information
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", result.getMessage());

            if (result.getRemainingLockTime() > 0) {
                response.put("locked", true);
                response.put("remainingLockTime", result.getRemainingLockTime());
            } else {
                response.put("remainingAttempts", result.getRemainingAttempts());
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
}