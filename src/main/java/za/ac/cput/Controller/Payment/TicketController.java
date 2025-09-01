package za.ac.cput.Controller.Payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.Domain.payment.Payment;
import za.ac.cput.Domain.payment.Ticket;
import za.ac.cput.Service.impl.PaymentService;
import za.ac.cput.Service.impl.TicketService;

import java.util.List;

@RestController
@RequestMapping("/tickets")
@CrossOrigin(origins = "http://localhost:3000")
public class TicketController {

    private final TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping("/create")
    public ResponseEntity<Ticket> create(@RequestBody Ticket ticket) {
        Ticket created = ticketService.create(ticket);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<Ticket> read(@PathVariable Integer id) {
        Ticket admin = ticketService.read(id);
        return ResponseEntity.ok(admin);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Ticket>> getAllTickets() {
        return ResponseEntity.ok(ticketService.getTickets());
    }

}
