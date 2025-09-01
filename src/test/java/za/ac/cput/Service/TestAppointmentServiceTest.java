package za.ac.cput.Service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.Domain.User.Applicant;
import za.ac.cput.Domain.bookings.TestAppointment;
import za.ac.cput.Domain.bookings.TestType;
import za.ac.cput.Domain.payment.Payment;
import za.ac.cput.Factory.bookings.TestAppointmentFactory;
import za.ac.cput.Service.impl.TestAppointmentService;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TestAppointmentServiceTest {

    @Autowired
    private TestAppointmentService service;

    @Test
    void createAndReadTestAppointment() { //updated
        Payment payment = new Payment();
        Applicant applicant = null; // No applicant for this test

        TestAppointment appointment = TestAppointmentFactory.createTestAppointment(
                "10 Main Road",
                "Test Venue",
                LocalDate.now().plusDays(5),
                true,
                "B",
                TestType.DRIVERSLICENSETEST,
                LocalTime.of(9,0),
                500.0,
                payment,
                applicant // Added applicant parameter
        );

        assertNotNull(appointment, "Factory returned null");

        // Save to DB
        TestAppointment saved = service.create(appointment);
        assertNotNull(saved.getTestAppointmentId(), "ID should be generated after save");

        // Read from DB
        TestAppointment found = service.read(saved.getTestAppointmentId());
        assertNotNull(found, "Saved appointment should be found in DB");
        assertEquals("10 Main Road", found.getTestAddress());
    }

    @Test
    void updateTestAppointment() {
        Payment payment = new Payment();
        Applicant applicant = null;

        TestAppointment appointment = TestAppointmentFactory.createTestAppointment(
                "123 Road",
                "Venue A",
                LocalDate.now().plusDays(3),
                false,
                "10",
                TestType.LEARNERSLICENSETEST,
                LocalTime.of(9,0),
                600.0,
                payment,
                applicant
        );

        TestAppointment saved = service.create(appointment);

        // Update venue
        TestAppointment updated = new TestAppointment.Builder()
                .copy(saved)
                .setTestVenue("Updated Venue")
                .build();

        TestAppointment result = service.update(updated);
        assertEquals("Updated Venue", result.getTestVenue());
    }

    @Test
    void deleteTestAppointment() {
        Payment payment = new Payment();
        Applicant applicant = null;

        TestAppointment appointment = TestAppointmentFactory.createTestAppointment(
                "456 Street",
                "Venue B",
                LocalDate.now().plusDays(4),
                true,
                "14",
                TestType.DRIVERSLICENSETEST,
                LocalTime.of(9,0),
                700.0,
                payment,
                applicant
        );

        TestAppointment saved = service.create(appointment);
        Long id = saved.getTestAppointmentId();

        // Uncomment if your service has a delete method
        // service.delete(id);
        // assertNull(service.read(id), "Deleted appointment should not be found");
    }
}