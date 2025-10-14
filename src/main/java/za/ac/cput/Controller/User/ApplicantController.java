package za.ac.cput.Controller.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.Domain.User.Applicant;
import za.ac.cput.Service.impl.ApplicantService;

import java.util.List;
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
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Applicant loginRequest) {

        Optional<Applicant> applicantOpt = applicantService.getAll().stream()
                .filter(a -> a.getContact() != null
                        && a.getContact().getEmail().equalsIgnoreCase(loginRequest.getContact().getEmail()))
                .findFirst();

        if (applicantOpt.isPresent()) {
            Applicant applicant = applicantOpt.get();
            if (loginRequest.getPassword().equals(applicant.getPassword())) {
                // Return the full applicant object
                return ResponseEntity.ok(applicant);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect password.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Applicant not found.");
        }
    }

}