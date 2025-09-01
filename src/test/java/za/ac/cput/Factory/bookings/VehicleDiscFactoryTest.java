package za.ac.cput.Factory.bookings;

import org.junit.jupiter.api.Test;
import za.ac.cput.Domain.bookings.VehicleDisc;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
class VehicleDiscFactoryTest {

    public static VehicleDisc disc = VehicleDiscFactory.createVehicleDisc(LocalDate.of(2020, 8, 6), LocalDate.of(2024, 2, 6)); // should pass
    public static VehicleDisc disc1 = VehicleDiscFactory.createVehicleDisc(LocalDate.of(2020, 8, 6), LocalDate.of(2026, 2, 6)); // should fail

    @Test
    void createVehicleDisc() {
        assertNotNull(disc);
        System.out.println(disc);
    }
    @Test
    void shouldfail(){
        assertNull(disc1);
        System.out.println(disc1);
    }
}