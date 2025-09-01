package za.ac.cput.Controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import za.ac.cput.Domain.User.Applicant;
import za.ac.cput.Domain.contact.Address;
import za.ac.cput.Domain.contact.Contact;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApplicantControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String getBaseUrl() {
        return "http://localhost:" + port + "/api/applicants";
    }

    @Test
    void testCreateApplicant() {
        Contact contact = new Contact.Builder()
                .setContactId(1)
                .setCellphone("0740000000")
                .setEmail("applicant@example.com")
                .build();

        Address address = new Address.Builder()
                .setAddressId(1)
                .setStreet("Main Street")
                .setCity("Cape Town")
                .setProvince("WC")
                .setCountry("South Africa")
                .build();

        Applicant applicant = new Applicant.Builder()
                .setLastName("Test")
                .setFirstName("User")
                .setIdNumber("9001015800087")
                .setBirthDate(LocalDate.of(1990, 1, 1))
                .setContact(contact)
                .setAddress(address)
                .setRole(Applicant.Role.APPLICANT)
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Applicant> request = new HttpEntity<>(applicant, headers);

        ResponseEntity<Applicant> response = restTemplate.postForEntity(getBaseUrl() + "/create", request, Applicant.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Test", response.getBody().getFirstName());
    }
}
