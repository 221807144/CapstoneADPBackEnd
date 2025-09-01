package za.ac.cput.Factory.User;

import za.ac.cput.Domain.Registrations.Vehicle;
import za.ac.cput.Domain.User.Applicant;
import za.ac.cput.Domain.User.License;
import za.ac.cput.Domain.User.User;
import za.ac.cput.Domain.bookings.Bookings;
import za.ac.cput.Domain.contact.Address;
import za.ac.cput.Domain.contact.Contact;
import za.ac.cput.Util.Helper;

import java.time.LocalDate;
import java.util.List;

public class ApplicantFactory {
    public static Applicant createApplicant(String idNumber,String firstName, String lastName,
                                            Contact contact, Address address, License license,
                                            Bookings bookings, User.Role role ,  List<Vehicle> vehicle ,String password) {

        if (Helper.isNullOrEmpty(firstName) || Helper.isNullOrEmpty(lastName) ||
                contact == null || address == null || license == null || bookings == null ||
                role == null  || vehicle == null) {
            return null;
        }

        LocalDate birthDate = Helper.getDateOfBirth(idNumber);

        if (!Helper.isValidEmail(contact.getEmail())) {
            return null;
        }

        return new Applicant.Builder()
                .setFirstName(firstName)
                .setLastName(lastName)
                .setIdNumber(idNumber)
                .setBirthDate(birthDate)
                .setContact(contact)
                .setAddress(address)
                .setLicense(license)
                .setBookings(bookings)
                .setRole(role)
                .setVehicle(vehicle)

                .setPassword(password)
                .build();
    }
}
