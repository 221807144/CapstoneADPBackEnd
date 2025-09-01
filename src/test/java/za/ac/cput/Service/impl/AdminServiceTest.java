package za.ac.cput.Service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.Domain.User.Admin;
import za.ac.cput.Domain.contact.Contact;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AdminServiceTest {

    @Autowired
    private AdminService adminService;

    private Admin admin;

    @BeforeEach
    void setUp() {
        Contact contact = new Contact.Builder()
                .setContactId(2)
                .setCellphone("0730000000")
                .setEmail("admin@example.com")
                .build();

        admin = new Admin.Builder()
                .setFirstName("Alice")
                .setFirstName("Smith")
                .setContact(contact)
                .setRole(Admin.Role.ADMIN)
                .build();
    }

    @Test
    void testCreateAndReadAdmin() {
        Admin saved = adminService.create(admin);
        assertNotNull(saved);
        assertTrue(saved.getUserId() > 0);

        Admin found = adminService.read(saved.getUserId());
        assertEquals("Alice", found.getLastName());
    }

    @Test
    void testUpdateAdmin() {
        Admin saved = adminService.create(admin);
        saved.getFirstName();

        Admin updated = adminService.update(saved);
        assertEquals("UpdatedAdmin", updated.getFirstName());
    }

//    @Test
//    void testDeleteAdmin() {
//        Admin saved = adminService.create(admin);
//        boolean deleted = adminService.delete(saved.getUserId());
//        assertTrue(deleted);
//
//        Admin afterDelete = adminService.read(saved.getUserId());
//        assertNull(afterDelete);
//    }

    @Test
    void testGetAllAdmins() {
        adminService.create(admin);
        List<Admin> admins = adminService.getAllAdmins();
        assertFalse(admins.isEmpty());
    }
}
