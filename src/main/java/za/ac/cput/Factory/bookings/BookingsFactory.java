package za.ac.cput.Factory.bookings;

import za.ac.cput.Domain.bookings.Bookings;
import za.ac.cput.Domain.bookings.TestAppointment;
import za.ac.cput.Domain.bookings.VehicleDisc;
import za.ac.cput.Util.Helper;

import java.time.LocalDate;

public class BookingsFactory {

    public static Bookings createBookingsFactory(String bookingType, LocalDate bookingDate, VehicleDisc vehicleDisc, TestAppointment test) {
        Long bookingId = (long) Helper.generateUniqueId();

        if (Helper.isNullOrEmpty(bookingType) || bookingDate == null || vehicleDisc == null || test == null) {
            return null;
        }
        return new Bookings.Builder()
                .setBookingId(bookingId)
                .setBooktype(Bookings.Booktype.valueOf(bookingType))
                .setBookingDate(bookingDate)
                .setVehicleDisc(vehicleDisc)
                .setTest(test)
                .build();
    }

}
