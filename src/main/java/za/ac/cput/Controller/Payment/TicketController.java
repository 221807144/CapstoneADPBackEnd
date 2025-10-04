package za.ac.cput.Controller.Payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.Domain.User.Applicant;
import za.ac.cput.Domain.payment.Payment;
import za.ac.cput.Domain.payment.Ticket;
import za.ac.cput.Service.impl.PaymentService;
import za.ac.cput.Service.impl.TicketService;

import java.util.List;

@RestController
@RequestMapping("/tickets")
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
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

    @PutMapping("/update")
    public ResponseEntity<Ticket> update(@RequestBody Ticket ticket) {
        Ticket updated = ticketService.update(ticket);
        if (updated == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Ticket>> getAllTickets() {
        return ResponseEntity.ok(ticketService.getTickets());
    }

    @GetMapping("/vehicle/{vehicleId}")
    public ResponseEntity<List<Ticket>> getTicketsByVehicle(@PathVariable Integer vehicleId) {
        List<Ticket> tickets = ticketService.getTicketsByVehicleId(vehicleId);
        return tickets.isEmpty() ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : ResponseEntity.ok(tickets);
    }

    @GetMapping("/applicant/{applicantId}")
    public ResponseEntity<List<Ticket>> getTicketsByApplicant(@PathVariable Integer applicantId) {
        List<Ticket> tickets = ticketService.getTicketsByApplicantId(applicantId);
        return tickets.isEmpty() ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : ResponseEntity.ok(tickets);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<Ticket> updateTicket(
            @PathVariable int id,
            @RequestBody Ticket ticket) {
        Ticket updated = ticketService.updateTicket(id, ticket);
        return ResponseEntity.ok(updated);
    }

}
