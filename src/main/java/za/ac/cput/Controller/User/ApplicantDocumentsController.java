package za.ac.cput.Controller.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.Domain.User.Applicant;
import za.ac.cput.Service.impl.ApplicantService;

import java.time.LocalDate;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/applicant/documents")
public class ApplicantDocumentsController {

    @Autowired
    private ApplicantService applicantService;

    // Save License for applicant
    @PostMapping("/{applicantId}/license")
    public ResponseEntity<?> saveLicense(
            @PathVariable Integer applicantId,
            @RequestBody Map<String, String> request) {

        try {
            System.out.println(" Saving license for applicant: " + applicantId);

            Map<String, Object> docData = Map.of(
                    "type", "LICENSE",
                    "code", request.get("licenseCode"),
                    "issueDate", request.get("issueDate"),
                    "expiryDate", request.get("expiryDate")
            );

            Applicant updatedApplicant = applicantService.saveOrUpdateDocuments(applicantId, docData);

            System.out.println("License saved successfully for applicant: " + applicantId);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "License saved successfully"
            ));

        } catch (RuntimeException e) {
            System.out.println(" Error saving license: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("success", false, "message", e.getMessage()));
        } catch (Exception e) {
            System.out.println("Unexpected error saving license: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", "Error saving license: " + e.getMessage()));
        }
    }

    // Save Learners for applicant
    @PostMapping("/{applicantId}/learners")
    public ResponseEntity<?> saveLearners(
            @PathVariable Integer applicantId,
            @RequestBody Map<String, String> request) {

        try {
            System.out.println("Saving learners for applicant: " + applicantId);

            Map<String, Object> docData = Map.of(
                    "type", "LEARNERS",
                    "code", request.get("learnersCode"),
                    "issueDate", request.get("issueDate"),
                    "expiryDate", request.get("expiryDate")
            );

            Applicant updatedApplicant = applicantService.saveOrUpdateDocuments(applicantId, docData);

            System.out.println(" Learners saved successfully for applicant: " + applicantId);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Learners license saved successfully"
            ));

        } catch (RuntimeException e) {
            System.out.println(" Error saving learners: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("success", false, "message", e.getMessage()));
        } catch (Exception e) {
            System.out.println(" Unexpected error saving learners: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", "Error saving learners license: " + e.getMessage()));
        }
    }
}