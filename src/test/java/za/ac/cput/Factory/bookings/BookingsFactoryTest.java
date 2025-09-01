package za.ac.cput.Factory.bookings;

import org.junit.jupiter.api.Test;
import za.ac.cput.Domain.bookings.Bookings;
import za.ac.cput.Domain.bookings.TestAppointment;
import za.ac.cput.Domain.bookings.VehicleDisc;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class BookingsFactoryTest {

    @Test
    void testCreateBookingsFactory_ValidInput() {
        VehicleDisc vehicleDisc = new VehicleDisc();
        TestAppointment testAppointment = new TestAppointment();
        LocalDate bookingDate = LocalDate.of(2025, 8, 6);

        Bookings booking = BookingsFactory.createBookingsFactory(
                "TEST",
                bookingDate,
                vehicleDisc,
                testAppointment
        );

        assertNotNull(booking);
        assertEquals(Bookings.Booktype.TEST, booking.getBooktype());
        assertEquals(bookingDate, booking.getBookingDate());
        assertEquals(vehicleDisc, booking.getVehicleDisc());
        assertEquals(testAppointment, booking.getTest());

        System.out.println("Booking created: " + booking);
    }

    @Test
    void testCreateBookingsFactory_NullInput() {
        Bookings booking = BookingsFactory.createBookingsFactory(
                null,
                null,
                null,
                null
        );
        assertNull(booking);
        System.out.println("Booking creation failed as expected due to null input.");
    }
}



