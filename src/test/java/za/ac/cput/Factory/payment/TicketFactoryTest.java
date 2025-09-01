package za.ac.cput.Factory.payment;


import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.Domain.payment.Payment;
import za.ac.cput.Domain.payment.Ticket;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class TicketFactoryTest {

    Ticket ticketWithoutPayment = TicketFactory.createTicket("DRUNK_DRIVING");

    //Ticket with Payment

    LocalDate date =  LocalDate.now();

    Payment payment = PaymentFactory.createPayment(2000.0,"Card","Thando", 4538483625767l ,date,"Ticket", (short) 464);

    Ticket ticketWithPayment = TicketFactory.createTicket("NO_LICENSE",payment);

    @Test
    public void a_isTicketWithoutPaymentNull(){
        assertNotNull(ticketWithoutPayment);
        System.out.println(ticketWithoutPayment.toString());
    }

    @Test
    public void b_isTicketWithPaymentNull(){
        assertNotNull(ticketWithPayment);
        System.out.println(ticketWithPayment.toString());
    }

}