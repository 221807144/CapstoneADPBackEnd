package za.ac.cput.Factory.Registration;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.ac.cput.Domain.Registrations.Registration;
import za.ac.cput.Domain.Registrations.Vehicle;

import static org.junit.jupiter.api.Assertions.*;

public class RegistrationFactoryTest {

    private Vehicle vehicle;

    @BeforeEach
    void setUp() {
        // You can also use a VehicleFactory if you have one
        vehicle = new Vehicle.Builder()
                .setVehicleID(1)
                .setVehicleName("Toyota Corolla")
                .setVehicleType("Sedan")
                .setVehicleModel("2020")
                .setVehicleYear("2020")
                .setVehicleColor("White")
                .build();
    }

    @Test
    void createRegistration_successful() {
        Registration registration = RegistrationFactory.createRegistration(
                "REG2025-01",
                "2025-08-06",
                vehicle
        );

        assertNotNull(registration);
        assertEquals("REG2025-01", registration.getRegistrationNumber());
        assertEquals("2025-08-06", registration.getRegistrationDate());
        assertNotNull(registration.getVehicle());
        assertEquals("Toyota Corolla", registration.getVehicle().getVehicleName());
    }

    @Test
    void read() {
        Registration registration = RegistrationFactory.createRegistration(
                "98776",
                "2025-08-06",
                vehicle
        );
        //System.out.println("Registration: " + registration);
        assertNull(registration);
    }

    @Test
    void getAll() {
        Registration registration = RegistrationFactory.createRegistration(
                "REG2025-01",
                "2025-08-06",
                null
        );
//        assertNull(registration);
    }
}
