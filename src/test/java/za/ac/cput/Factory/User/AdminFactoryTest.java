package za.ac.cput.Factory.User;

import org.junit.jupiter.api.Test;
import za.ac.cput.Domain.User.Admin;
import za.ac.cput.Domain.User.User;
import za.ac.cput.Domain.bookings.Bookings;
import za.ac.cput.Domain.contact.Contact;
import za.ac.cput.Domain.payment.Payment;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AdminFactoryTest {

    Contact getContact() {
        return new Contact.Builder()
                .setEmail("asisiphopeter@gmail.com")
                .setCellphone("0655528976")
                .build();
    }

    Bookings getBooking() {
        return new Bookings.Builder()
//                .setBookingId(1)
                .setBookingDate(LocalDate.now())
                .build();
    }

    List<Payment> getPayments() {
        Payment payment = new Payment.Builder()
                .setPaymentType(Payment.PaymentType.Ticket)
                .setPaymentAmount(250.00)
                .setPaymentDate(LocalDate.now())
                .build();
        return Collections.singletonList(payment);
    }
//
//    @Test
//    void testCreateAdmin_shouldNotBeNull() {
//        Admin admin = AdminFactory.createAdmin(
//                "Masibuve",
//                "Sikhulume",
//                getContact(),
//                getBooking(),
//                User.Role.ADMIN,
//                getPayments(),
//                Admin.Status.ACCEPTED,
//                "System Manager"
//        );
//        assertNotNull(admin);
//    }
//
//    @Test
//    void testCreateAdmin_valuesShouldMatch() {
//        Admin admin = AdminFactory.createAdmin(
//                "Masibuve",
//                "Sikhulume",
//                getContact(),
//                getBooking(),
//                User.Role.ADMIN,
//                getPayments(),
//                Admin.Status.ACCEPTED,
//                "System Manager"
//        );
//
//        assertEquals("Masibuve", admin.getFirstName());
//        assertEquals("Sikhulume", admin.getLastName());
//        assertEquals("admin@example.com", admin.getContact().getEmail());
//        assertEquals(Admin.Status.ACCEPTED, admin.getStatus());
//    }
//
//    @Test
//    void testCreateAdmin_printOutput() {
//        Admin admin = AdminFactory.createAdmin(
//                "Masibuve",
//                "Sikhulume",
//                getContact(),
//                getBooking(),
//                User.Role.ADMIN,
//                getPayments(),
//                Admin.Status.ACCEPTED,
//                "System Manager"
//        );
//        System.out.println(admin);
//    }
}
