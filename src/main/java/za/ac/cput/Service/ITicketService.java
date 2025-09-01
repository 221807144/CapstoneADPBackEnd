package za.ac.cput.Service;

import org.springframework.stereotype.Service;
import za.ac.cput.Domain.payment.Ticket;

import java.util.List;

public interface ITicketService extends IService<Ticket, Integer> {
    List<Ticket> getTickets();
}
