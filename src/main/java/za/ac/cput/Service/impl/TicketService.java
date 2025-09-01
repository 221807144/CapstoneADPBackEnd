package za.ac.cput.Service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.Domain.payment.Ticket;
import za.ac.cput.Repository.TicketRepository;
import za.ac.cput.Service.ITicketService;

import java.util.List;

@Service
public class TicketService implements ITicketService {


    private final TicketRepository ticketRepository;

    @Autowired
    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public Ticket create(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    @Override
    public Ticket read(Integer integer) {
        return ticketRepository.findById(integer).orElse(null);
    }

    @Override
    public Ticket update(Ticket ticket) {
        if (ticketRepository.existsById(ticket.getTicketId())) {
            return ticketRepository.save(ticket);
        }
        return null;
    }

    @Override
    public List<Ticket> getTickets() {
        return this.ticketRepository.findAll();
    }

//    @Deprecated
//    @Override
//    public void delete(Integer integer) {
//    }
}
