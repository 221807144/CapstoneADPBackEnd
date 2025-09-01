package za.ac.cput.Service;

import org.springframework.stereotype.Service;
import za.ac.cput.Domain.payment.Payment;
import za.ac.cput.Domain.payment.Ticket;

import java.util.List;

@Service
public interface IPaymentService extends IService<Payment, Integer>{
    public List<Payment> getpayments();
}
