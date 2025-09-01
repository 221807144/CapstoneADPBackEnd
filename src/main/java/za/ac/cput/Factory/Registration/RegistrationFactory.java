package za.ac.cput.Factory.Registration;

import za.ac.cput.Domain.Registrations.Registration;
import za.ac.cput.Domain.Registrations.Vehicle;
import za.ac.cput.Util.Helper;

public class RegistrationFactory {

    public static Registration createRegistration(String registrationNumber, String registrationDate, Vehicle vehicle) {
        if (Helper.isNullOrEmpty(registrationNumber)
                || Helper.isNullOrEmpty(registrationDate)
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

