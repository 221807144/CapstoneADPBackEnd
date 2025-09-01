package za.ac.cput.Factory.payment;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.Domain.payment.Payment;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class PaymentFactoryTest {

    LocalDate date = LocalDate.now();

    Payment payment = PaymentFactory.createPayment(2000.0,"Card","Thando", 4538483625767l ,date,"Ticket", (short) 464);

    Payment payment2 = PaymentFactory.createPayment(2000.0,"Card","Ticket");

    @Test
    public void a_paymentWithCardNotNull(){
        assertNotNull(payment);
        System.out.println(payment);
    }

    @Test
    public void b_paymentWithCashNotNull(){
        assertNotNull(payment2);
        System.out.println(payment2);
    }

}