package za.ac.cput.Service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.Domain.payment.Payment;
import za.ac.cput.Repository.PaymentRepository;
import za.ac.cput.Service.IPaymentService;

import java.util.List;

@Service
public class PaymentService implements IPaymentService {


    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }


    @Override
    public Payment create(Payment payment) {
        return paymentRepository.save(payment);
    }

    @Override
    public Payment read(Integer integer) {
        return paymentRepository.findById(integer).orElse(null);
    }

    @Override
    public Payment update(Payment payment) {
        if (paymentRepository.existsById(payment.getPaymentId())) {
            return paymentRepository.save(payment);
        }
        return null;
    }

//    @Deprecated
//    @Override
//    public void delete(Integer integer) {
//        paymentRepository.deleteById(integer);
//    }

    @Override
    public List<Payment> getpayments() {
        return this.paymentRepository.findAll();
    }
}
