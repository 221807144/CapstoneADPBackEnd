package za.ac.cput.Controller.Registration;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import za.ac.cput.Domain.Registrations.Vehicle;
import za.ac.cput.Domain.User.Applicant;
import za.ac.cput.Domain.bookings.VehicleDisc;
import za.ac.cput.Domain.payment.Payment;
import za.ac.cput.Domain.payment.Ticket;
import za.ac.cput.Factory.Registration.VehicleFactory;
import za.ac.cput.Factory.bookings.VehicleDiscFactory;
import za.ac.cput.Factory.payment.PaymentFactory;
import za.ac.cput.Factory.payment.TicketFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.MethodName.class)

class VehicleControllerTest {
    private static VehicleDisc disc;

    private static List<Ticket> ticketList ;
    private static Applicant applicant;
    private static Vehicle vehicle;
    private static Vehicle createdVehicle;

//    private static Payment payment1,payment2;


    @Autowired
    private TestRestTemplate restTemplate;
    private static final String BASE_URL = "http://localhost:8081/capstone/vehicle";

    private static List<Ticket> paymentTicketList(){
        List<Ticket> ticketList = new ArrayList<>();

        LocalDate date = LocalDate.now();

        Payment payment1 = PaymentFactory.createPayment(2000.0,"Card","Thando", 4538483625767l ,date,"Ticket", (short) 464);
        Ticket ticket1 = TicketFactory.createTicket("NO_LICENSE");

        Payment payment2 = PaymentFactory.createPayment(2000.0,"Card","Thando", 4538483625767l ,date,"Ticket", (short) 464);
        Ticket ticket2 = TicketFactory.createTicket("NO_LICENSE", payment2);

        ticketList.add(ticket1);
        ticketList.add(ticket2);
        return ticketList;
    }

    @BeforeAll
    static void setUp() {
        ticketList = paymentTicketList();

        // Create Payment objects
//        private static Payment payment1 = PaymentFactory.createPayment(2000, "PayPal", "Payment for Speeding Ticket");
//        private static  Payment payment2 = PaymentFactory.createPayment(1500, "Credit Card", "Payment for Parking Ticket");

        // Create Tickets
//        Ticket ticket1 = TicketFactory.createTicket(3000, "Pending", payment1);
//        Ticket ticket2 = TicketFactory.createTicket(2500, "Paid", payment2);
//
//        ticketList = new ArrayList<>();
//        ticketList.add(ticket1);
//        ticketList.add(ticket2);

        // Create VehicleDisc
          disc = VehicleDiscFactory.createVehicleDisc(LocalDate.of(2020, 8, 6), LocalDate.of(2024, 2, 6));

        // Create Vehicle (not saved yet)
  vehicle = VehicleFactory.createvehicle(
                "Toyota",
                "SUV",
                "Fortuner",
                "2023",
                "White",
          "CAA1234",
          "zaa098",
                disc,
               null,
               null,
                 applicant
        );

    }

    @Test
    void a_create() {
       assertNotNull(vehicle);
        String Url = BASE_URL + "/create";
        ResponseEntity<Vehicle> response = restTemplate.postForEntity(Url, vehicle, Vehicle.class);
        System.out.println("Vehicle to create: " + vehicle.toString());

        assertNotNull(response);
        System.out.println(response.getBody());


        Vehicle responseVehicle = response.getBody();
        assertNotNull(responseVehicle);
        assertEquals(vehicle.getVehicleID(), responseVehicle.getVehicleID());
        assertEquals(vehicle.getVehicleName(), responseVehicle.getVehicleName());


        createdVehicle = response.getBody();
//        vehicle = responseVehicle;
        System.out.println(responseVehicle.toString());
    }

    @Test
    void read() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void getAll() {
    }
}