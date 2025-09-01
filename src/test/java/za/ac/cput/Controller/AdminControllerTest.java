package za.ac.cput.Controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import za.ac.cput.Domain.User.Admin;
import za.ac.cput.Domain.contact.Contact;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AdminControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String getBaseUrl() {
        return "http://localhost:" + port + "/admin";
    }

    @Test
    void testCreateAdmin() {
        Contact contact = new Contact.Builder()
                .setContactId(2)
                .setCellphone("0730000000")
                .setEmail("admin@example.com")
                .build();

        Admin admin = new Admin.Builder()
                .setFirstName("Alice")
                .setLastName("Smith")
                .setContact(contact)
                .setRole(Admin.Role.ADMIN)
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Admin> request = new HttpEntity<>(admin, headers);

        ResponseEntity<Admin> response = restTemplate.postForEntity(getBaseUrl() + "/create", request, Admin.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Alice", response.getBody().getLastName());
    }
}
