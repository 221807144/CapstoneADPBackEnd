package za.ac.cput.Factory.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.ac.cput.Domain.Registrations.Vehicle;
import za.ac.cput.Domain.User.Applicant;
import za.ac.cput.Domain.User.License;
import za.ac.cput.Domain.User.User;
import za.ac.cput.Domain.bookings.Bookings;
import za.ac.cput.Domain.bookings.VehicleDisc;
import za.ac.cput.Domain.contact.Address;
import za.ac.cput.Domain.contact.Contact;
import za.ac.cput.Domain.payment.Payment;
import za.ac.cput.Domain.payment.Ticket;
import za.ac.cput.Factory.Registration.VehicleFactory;
import za.ac.cput.Factory.bookings.VehicleDiscFactory;
import za.ac.cput.Factory.payment.PaymentFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicantFactoryTest {

    private Contact contact;
    private Address address;
    private License license;
    private Bookings bookings;
    private VehicleDisc disc;
    private List<Ticket> tickets;
    private List<Vehicle> vehicles;
    private Payment payment;

    @BeforeEach
    public void setUp() {
        contact = new Contact.Builder()
                .setEmail("applicant@email.com")
                .setCellphone("0812345678")
                .build();

        address = new Address.Builder()
                .setStreet("123 Main Street")
                .setCity("Cape Town")
                .setProvince("Western Cape")
                .setCountry("South Africa")
                .build();

        license = new License.Builder()
                .setlicenseCode("Code 10")
                .setIssueDate(LocalDate.of(2015, 1, 10))
                .setExpiryDate(LocalDate.of(2025, 1, 10))
                .build();

        bookings = new Bookings.Builder()
                .setBookingDate(LocalDate.now())
                .build();

        disc = VehicleDiscFactory.createVehicleDisc(
                LocalDate.of(2020, 8, 6),
                LocalDate.of(2024, 2, 6)
        );

        payment = PaymentFactory.createPayment(2000, "PayPal", "Payment for Ticket");
        tickets = new ArrayList<>();

        // ✅ Proper Vehicle creation
        Vehicle car = VehicleFactory.createvehicle(
                "Toyota",
                "SUV",
                "Fortuner",
                "2023",
                "White",
                "bwa345678",
                "1234567899876",
                disc,
                tickets,
                payment,
                null

        );

        vehicles = new ArrayList<>();
        vehicles.add(car);
    }

    @Test
    public void testCreateValidApplicant() {
        Applicant applicant = ApplicantFactory.createApplicant(
                "0001015009087",      // ✅ idNumber
                "Masibuve",           // ✅ firstName
                "Sikhulume",          // ✅ lastName
                contact,
                address,
                license,
                bookings,
                User.Role.APPLICANT,
                vehicles,
                "password123"         // ✅ added password
        );

        System.out.println(applicant);
        assertNotNull(applicant);
    }

    @Test
    public void testCreateApplicantWithInvalidEmail() {
        Contact badContact = new Contact.Builder()
                .setEmail("invalid-email")
                .setCellphone("0812345678")
                .build();

        Applicant applicant = ApplicantFactory.createApplicant(
                "0001015009087",
                "Test",
                "User",
                badContact,
                address,
                license,
                bookings,
                User.Role.APPLICANT,
                vehicles,
                "password123"
        );

        System.out.println(applicant);
        assertNull(applicant);
    }

    @Test
    public void testCreateApplicantWithMissingName() {
        Applicant applicant = ApplicantFactory.createApplicant(
                "0001015009087",
                "",                   // ❌ missing firstName
                "Sikhulume",
                contact,
                address,
                license,
                bookings,
                User.Role.APPLICANT,
                vehicles,
                "password123"
        );

        System.out.println(applicant);
        assertNull(applicant);
    }
}
