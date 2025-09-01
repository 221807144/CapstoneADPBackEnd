package za.ac.cput.Factory.User;

import za.ac.cput.Domain.User.Admin;
import za.ac.cput.Domain.User.User;
import za.ac.cput.Domain.bookings.Bookings;
import za.ac.cput.Domain.contact.Address;
import za.ac.cput.Domain.contact.Contact;
import za.ac.cput.Domain.payment.Payment;
import za.ac.cput.Util.Helper;

import java.time.LocalDate;
import java.util.List;

public class AdminFactory {
    public static Admin createAdmin(String idNumber, String firstName, String lastName, Contact contact, Address address, Bookings bookings, User.Role role,
                                    List<Payment> payments,String password) {
        if (Helper.isNullOrEmpty(firstName) || Helper.isNullOrEmpty(lastName) ||
                contact == null || bookings == null || role == null || payments == null) {
            return null;
        }

        // FIXED: Only return null if reason is empty OR contact number is invalid OR email is invalid

        LocalDate birthDate = Helper.getDateOfBirth(idNumber);

        return new Admin.Builder()
                .setIdNumber(idNumber)
                .setFirstName(firstName)
                .setLastName(lastName)
                .setBirthDate(birthDate)
                .setContact(contact)
                .setAddress(address)
                .setBookings(bookings)
                .setRole(role)
                .setPayments(payments)
                .setPassword(password)
                .build();
    }
}
