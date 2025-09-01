package za.ac.cput.Service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.Domain.Registrations.Registration;
import za.ac.cput.Domain.Registrations.Vehicle;
import za.ac.cput.Factory.Registration.RegistrationFactory;
import za.ac.cput.Service.impl.RegistrationService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RegistrationServiceTest {

//    @Autowired
//    private VehicleRepository vehicleRepository;

    @Autowired
    private RegistrationService registrationService;

    @Test
    void testCreateAndRead() {
        Vehicle vehicle = new Vehicle.Builder()
                .setVehicleID(1)
                .setVehicleName("Toyota Corolla")
                .setVehicleType("Sedan")
                .setVehicleModel("2020")
                .setVehicleYear("2020")
                .setVehicleColor("White")
                .build();

        Registration registration = RegistrationFactory.createRegistration(
                "REG2025-01",
                "2025-08-06",
                vehicle
        );

        // Save to DB via service
        Registration saved = registrationService.create(registration);

        assertNotNull(saved.getRegistrationId(), "Registration ID should be generated");

        // Read from DB
        Registration found = registrationService.read(saved.getRegistrationId());
        assertNotNull(found);
        assertEquals("REG2025-01", found.getRegistrationNumber());
    }

    @Test
    void testUpdate() {
        Vehicle vehicle = new Vehicle.Builder()
                .setVehicleID(1)
                .setVehicleName("Toyota Corolla")
                .setVehicleType("Sedan")
                .setVehicleModel("2020")
                .setVehicleYear("2020")
                .setVehicleColor("White")
                .build();

        //Vehicle savedvehicle = VehicleRepository.save(vehicle);

        Registration registration = RegistrationFactory.createRegistration(
                "REG2025-01",
                "2025-08-06",
                vehicle
        );

        Registration saved = registrationService.create(registration);

        Registration updated = new Registration.Builder()
                .copy(saved)
                .setRegistrationNumber("REG-UPDATED")
                .build();

        Registration result = registrationService.update(updated);

        assertEquals("REG-UPDATED", result.getRegistrationNumber());
    }
//    @Test
//    void testSaveVehicle() {
//        Vehicle vehicle = new Vehicle.Builder().build();
//        vehicle.setVehicleID(1);
//        vehicle.setVehicleName("Toyota Corolla");
//
//        Vehicle savedVehicle = vehicleRepository.save(vehicle);
//        assertNotNull(savedVehicle.getVehicleID());
//    }


    @Test
    void testDelete() {
        Vehicle vehicle = new Vehicle.Builder()
                .setVehicleID(1)
                .setVehicleName("Toyota Corolla")
                .setVehicleType("Sedan")
                .setVehicleModel("2020")
                .setVehicleYear("2020")
                .setVehicleColor("White")
                .build();

        Registration registration = RegistrationFactory.createRegistration(
                "REG2025-01",
                "2025-08-06",
                vehicle
        );

        Registration saved = registrationService.create(registration);
        Integer id = saved.getRegistrationId();

//        registrationService.delete(id);

        assertNull(registrationService.read(id));
    }

    @Test
    void testGetAll() {
        Vehicle vehicle = new Vehicle.Builder()
                .setVehicleID(1)
                .setVehicleName("Toyota Corolla")
                .setVehicleType("Sedan")
                .setVehicleModel("2020")
                .setVehicleYear("2020")
                .setVehicleColor("White")
                .build();

        Registration registration1 = RegistrationFactory.createRegistration("REG2025-01", "2025-08-06", vehicle);
        Registration registration2 = RegistrationFactory.createRegistration("REG2025-02", "2025-08-07", vehicle);

        registrationService.create(registration1);
        registrationService.create(registration2);

        List<Registration> all = registrationService.getAll();
        assertTrue(all.size() >= 2);
    }
}
