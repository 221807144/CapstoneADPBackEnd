package za.ac.cput.Factory.bookings;

import org.junit.jupiter.api.Test;
import za.ac.cput.Domain.bookings.TestAppointment;
import za.ac.cput.Domain.bookings.TestType;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TestAppointmentFactoryTest {

    @Test
    void testCreateTestAppointment_shouldNotBeNull() {
        TestAppointment testAppointment = TestAppointmentFactory.createTestAppointment(
                "10 Main Road",
                "Test Venue",
                LocalDate.now().plusDays(3),
                true,
                "B",
                TestType.DRIVERSLICENSETEST
        );
        System.out.println("Test Appointment: " + testAppointment);
        assertNotNull(testAppointment);
    }

    @Test
    void testCreateTestAppointment_valuesShouldMatch() {
        Long id = 1001L;
        String address = "10 Main Road";
        String venue = "Test Venue";
        LocalDate date = LocalDate.now().plusDays(3);
        boolean result = true;
        String licenseCode = "10";
        TestType testType = TestType.DRIVERSLICENSETEST;

        TestAppointment testAppointment = TestAppointmentFactory.createTestAppointment(
                address, venue, date, result, licenseCode, testType
        );

        //System.out.println("Test Appointment: " + testAppointment);
        assertEquals(id, testAppointment.getTestAppointmentId());
        assertEquals(address, testAppointment.getTestAddress());
        assertEquals(venue, testAppointment.getTestVenue());
        assertEquals(date, testAppointment.getTestDate());
//        assertEquals(result, testAppointment.isTestResult());
        assertEquals(licenseCode, testAppointment.getLicenseCode());
        assertEquals(testType, testAppointment.getTestype());
    }

    @Test
    void testCreateTestAppointment_invalidData_shouldReturnNotNull() {
        // Null testType
        TestAppointment testAppointment = TestAppointmentFactory.createTestAppointment(
                 "123 Road", "Venue", LocalDate.now().plusDays(2), true, "B", null
        );
        assertNull(testAppointment);

        // Past test date
        testAppointment = TestAppointmentFactory.createTestAppointment(
                 "123 Road", "Venue", LocalDate.now().minusDays(2), true, "B", TestType.DRIVERSLICENSETEST
        );
        assertNull(testAppointment);
    }

    @Test
    void testCreateTestAppointment_printOutput() {
        TestAppointment testAppointment = TestAppointmentFactory.createTestAppointment(
                "123 Road", "Venue", LocalDate.now().plusDays(2), true, "B", TestType.DRIVERSLICENSETEST
        );
        System.out.println(testAppointment);
    }
}
