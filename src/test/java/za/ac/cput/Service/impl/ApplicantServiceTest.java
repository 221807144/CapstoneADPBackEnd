package za.ac.cput.Service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.Domain.User.Applicant;
import za.ac.cput.Domain.contact.Address;
import za.ac.cput.Domain.contact.Contact;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ApplicantServiceTest {

    @Autowired
    private ApplicantService applicantService;

    private Applicant applicant;

    @BeforeEach
    void setUp() {
        Contact contact = new Contact.Builder()
                .setContactId(1)
                .setCellphone("0740000000")
                .setEmail("applicant@example.com")
                .build();

        Address address = new Address.Builder()
                .setAddressId(1)
                .setStreet("45 Main Street")
                .setCity("Cape Town")
                .setProvince("Western Cape")
                .setCountry("South Africa")
                .build();

        applicant = new Applicant.Builder()
                .setLastName("John")
                .setLastName("Doe")
                .setIdNumber("9001015800087")
                .setBirthDate(LocalDate.of(1990, 1, 1))
                .setContact(contact)
                .setAddress(address)
                .setRole(Applicant.Role.APPLICANT)
                .build();

    }

    @Test
    void testCreateAndReadApplicant() {
        Applicant saved = applicantService.create(applicant);
        assertNotNull(saved);
        assertTrue(saved.getUserId() > 0);

        Applicant found = applicantService.read(saved.getUserId());
        assertNotNull(found);
        assertEquals("John", found.getFirstName());

    }

    @Test
    void testUpdateApplicant() {
        Applicant saved = applicantService.create(applicant);
        saved.setFirstName("UpdatedName");
        Applicant updated = applicantService.update(saved);
        assertEquals("UpdatedName", updated.getFirstName());

    }

//    @Test
//    void testDeleteApplicant() {
//        Applicant saved = applicantService.create(applicant);
//        applicantService.delete(saved.getUserId());
//
//        Applicant deleted = applicantService.read(saved.getUserId());
//        assertNull(deleted);
//    }

    @Test
    void testGetAllApplicants() {
        applicantService.create(applicant);
        List<Applicant> all = applicantService.getAll();
        assertFalse(all.isEmpty());
    }
}
