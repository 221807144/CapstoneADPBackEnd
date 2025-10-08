//package za.ac.cput.Factory.Registration;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import za.ac.cput.Domain.Registrations.Vehicle;
//import za.ac.cput.Domain.User.Applicant;
//import za.ac.cput.Domain.bookings.VehicleDisc;
//import za.ac.cput.Domain.payment.Payment;
//import za.ac.cput.Domain.payment.Ticket;
//import za.ac.cput.Factory.bookings.VehicleDiscFactory;
//import za.ac.cput.Factory.payment.PaymentFactory;
//import za.ac.cput.Factory.payment.TicketFactory;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class VehicleFactoryTest {
//
//        VehicleDisc disc;
//        List<Ticket> tickets;
//        Vehicle vehicle;
//        Applicant applicant;
//        @BeforeEach
//        void setup() {
//            disc = VehicleDiscFactory.createVehicleDisc(LocalDate.of(2020, 8, 6), LocalDate.of(2024, 2, 6));
//
//            LocalDate date = LocalDate.now();
//
//            Payment payment = PaymentFactory.createPayment(2000.0,"Card","Thando", 4538483625767l ,date,"Ticket", (short) 464);
////            Ticket ticketWithPayment = TicketFactory.createTicket(3000, "Pending", payment);
//
//            tickets = new ArrayList<>();
////            tickets.add(ticketWithPayment);
//
//            vehicle = VehicleFactory.createvehicle(
//                    "Toyota",
//                    "SUV",
//                    "Fortuner",
//                    "2023",
//                    "White",
//                    "CAA1234",
//                    "zaa098",
//                    disc,
//                    tickets,
//                    payment,
//                    applicant
//            );
//        }
//
//        @Test
//        void createVehicleTest() {
//            assertNotNull(vehicle);
//            System.out.println(vehicle.toString());
//        }
//}