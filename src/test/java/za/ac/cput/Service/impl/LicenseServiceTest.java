package za.ac.cput.Service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import za.ac.cput.Domain.User.License;
import za.ac.cput.Repository.LicenseRepository;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class LicenseServiceTest {

    @Autowired
    private LicenseService licenseService;

    @Autowired
    private LicenseRepository licenseRepository; // Add this

    private License license;

    @BeforeEach
    void setUp() {
        licenseRepository.deleteAll(); // Clear database before each test

        LocalDate issueDate = LocalDate.of(2023, 1, 1);
        LocalDate expiryDate = issueDate.plusYears(5);

        license = new License.Builder()
                .setlicenseCode("AC12345")
                .setIssueDate(issueDate)
                .setExpiryDate(expiryDate)
                .build();
    }
    // ... rest of the test class


    @Test
    void testCreateAndReadLicense() {
        License saved = licenseService.create(license);
        assertNotNull(saved);
        assertTrue(saved.getLicenseId() > 0);

        License found = licenseService.read(saved.getLicenseId());
        assertNotNull(found);
        assertEquals("AC12345", found.getLicenseCode());
        assertEquals(LocalDate.of(2023, 1, 1), found.getIssueDate());
        assertEquals(LocalDate.of(2028, 1, 1), found.getExpiryDate());
    }

    @Test
    void testUpdateLicense() {
        License saved = licenseService.create(license);

        License updatedLicense = new License.Builder()
                .copy(saved)
                .setlicenseCode("AC54321")
                .setExpiryDate(saved.getExpiryDate().plusYears(1))
                .build();

        License updated = licenseService.update(updatedLicense);
        assertEquals("AC54321", updated.getLicenseCode());
        assertEquals(LocalDate.of(2029, 1, 1), updated.getExpiryDate());
    }

//    @Test
//    void testDeleteLicense() {
//        License saved = licenseService.create(license);
//        licenseService.delete(saved.getLicenseId());
//
//        License deleted = licenseService.read(saved.getLicenseId());
//        assertNull(deleted);
//    }

    @Test
    void testGetAllLicenses() {
        // First verify initial state is empty
        assertEquals(0, licenseService.getAll().size());

        licenseService.create(license);

        // Create a second license
        License secondLicense = new License.Builder()
                .setlicenseCode("AC67890")
                .setIssueDate(LocalDate.of(2023, 6, 1))
                .setExpiryDate(LocalDate.of(2028, 6, 1))
                .build();
        licenseService.create(secondLicense);

        List<License> all = licenseService.getAll();
        assertEquals(2, all.size());
        assertTrue(all.stream().anyMatch(l -> l.getLicenseCode().equals("AC12345")));
        assertTrue(all.stream().anyMatch(l -> l.getLicenseCode().equals("AC67890")));
    }

    @Test
    void testLicenseExpiryCalculation() {
        License saved = licenseService.create(license);
        assertEquals(5, saved.getIssueDate().until(saved.getExpiryDate()).getYears());
    }
}