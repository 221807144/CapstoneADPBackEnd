package za.ac.cput.Controller.Payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.Domain.Registrations.Registration;
import za.ac.cput.Domain.User.Admin;
import za.ac.cput.Domain.User.Applicant;
import za.ac.cput.Domain.bookings.Bookings;
import za.ac.cput.Domain.payment.Payment;
import za.ac.cput.Service.impl.AdminService;
import za.ac.cput.Service.impl.PaymentService;

import java.util.List;

@RestController
@RequestMapping("/payments")
@CrossOrigin(origins = "http://localhost:3000") // <-- allow React app
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/create")
    public ResponseEntity<Payment> create(@RequestBody Payment payment) {
        Payment created = paymentService.create(payment);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<Payment> read(@PathVariable Integer id) {
        Payment admin = paymentService.read(id);
        return ResponseEntity.ok(admin);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Payment>> getAllPayments() {
        return ResponseEntity.ok(paymentService.getpayments());
    }
}