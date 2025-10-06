package za.ac.cput.Factory.Registration;

import za.ac.cput.Domain.Registrations.Registration;
import za.ac.cput.Domain.Registrations.Vehicle;
import za.ac.cput.Util.Helper;

import java.time.LocalDate;

public class RegistrationFactory {

    public static Registration createRegistration(String registrationNumber, LocalDate registrationDate, Vehicle vehicle) {
        if (Helper.isNullOrEmpty(registrationNumber)

                || vehicle == null) {
            return null;
        }

        return new Registration.Builder()
                .setRegistrationNumber(registrationNumber)
                .setRegistrationDate(registrationDate)
                .setVehicle(vehicle) // include vehicle
                .build();
    }
}

