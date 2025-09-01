package za.ac.cput.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import za.ac.cput.Domain.bookings.Bookings;
import za.ac.cput.Domain.bookings.Bookings.Booktype;
import za.ac.cput.Domain.bookings.TestAppointment;
import za.ac.cput.Domain.bookings.VehicleDisc;
import za.ac.cput.Repository.BookingsRepository;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class BookingsServiceTest {

    @Autowired
    private IBookingsService bookingsService;

    @Autowired
    private BookingsRepository bookingsRepository;

    private Bookings vehicleDiscBooking;
    private Bookings testAppointmentBooking;
    private VehicleDisc testVehicleDisc;
    private TestAppointment testAppointment;

    @BeforeEach
    void setUp() {
        bookingsRepository.deleteAll();

        testVehicleDisc = new VehicleDisc.Builder()
                .setDiscId(1L)
                .build();

        testAppointment = new TestAppointment.Builder()
                .setTestAppointmentId(1L)
                .build();

        vehicleDiscBooking = new Bookings.Builder()
                .setBooktype(Booktype.VEHICLE_DISC)
                .setBookingDate(LocalDate.now())
                .setVehicleDisc(testVehicleDisc)
                .build();

        testAppointmentBooking = new Bookings.Builder()
                .setBooktype(Booktype.TEST)
                .setBookingDate(LocalDate.now().plusDays(1))
                .setTest(testAppointment)
                .build();
    }

    @Test
    void testCreateAndReadVehicleDiscBooking() {
        Bookings saved = bookingsService.create(vehicleDiscBooking);
        assertNotNull(saved);
        assertNotNull(saved.getBookingId());
        assertEquals(Booktype.VEHICLE_DISC, saved.getBooktype());
        assertNotNull(saved.getVehicleDisc());
        assertEquals(1L, saved.getVehicleDisc().getDiscId());
    }

    @Test
    void testCreateAndReadTestAppointmentBooking() {
        Bookings saved = bookingsService.create(testAppointmentBooking);
        assertNotNull(saved);
        assertNotNull(saved.getBookingId());
        assertEquals(Booktype.TEST, saved.getBooktype());
        assertNotNull(saved.getTest());
        assertEquals(1L, saved.getTest().getTestAppointmentId());
    }

    @Test
    void testUpdateBooking() {
        Bookings saved = bookingsService.create(vehicleDiscBooking);

        Bookings updated = new Bookings.Builder()
                .copy(saved)
                .setBooktype(Booktype.TEST)
                .setVehicleDisc(null)
                .setTest(testAppointment)
                .build();

        Bookings result = bookingsService.update(updated);
        assertNotNull(result);
        assertEquals(Booktype.TEST, result.getBooktype());
        assertNull(result.getVehicleDisc());
        assertNotNull(result.getTest());
    }

//    @Test
//    void testDeleteBooking() {
//        Bookings saved = bookingsService.create(vehicleDiscBooking);
//        assertNotNull(saved.getBookingId());
//
//        boolean deleteResult = bookingsService.delete(saved.getBookingId());
//        assertTrue(deleteResult);
//
//        Bookings deleted = bookingsService.read(saved.getBookingId());
//        assertNull(deleted);
//    }

    @Test
    void testGetAllBookings() {
        bookingsService.create(vehicleDiscBooking);
        bookingsService.create(testAppointmentBooking);

        List<Bookings> all = bookingsService.getAll(); // Note the lowercase method name
        assertEquals(2, all.size());
        assertTrue(all.stream().anyMatch(b -> b.getBooktype() == Booktype.VEHICLE_DISC));
        assertTrue(all.stream().anyMatch(b -> b.getBooktype() == Booktype.TEST));
    }

    @Test
    void testBookingTypeValidation() {
        assertThrows(IllegalArgumentException.class, () -> new Bookings.Builder()
                .setBooktype(Booktype.VEHICLE_DISC)
                .setVehicleDisc(testVehicleDisc)
                .setTest(testAppointment)
                .build());
    }
}