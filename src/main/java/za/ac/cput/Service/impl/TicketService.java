package za.ac.cput.Service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.Domain.payment.Ticket;
import za.ac.cput.Repository.TicketRepository;
import za.ac.cput.Service.ITicketService;

import java.util.List;
import java.util.Optional;

@Service
public class TicketService implements ITicketService {

    private final TicketRepository ticketRepository;

    @Autowired
    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public Ticket create(Ticket ticket) {
        if (ticket.getTicketType() != null) {
            ticket = new Ticket.Builder()
                    .copy(ticket)
                    .setTicketType(ticket.getTicketType())
                    .build();
        }
        return ticketRepository.save(ticket);
    }

    @Override
    public Ticket read(Integer id) {
        return ticketRepository.findById(id).orElse(null);
    }

    @Override
    public Ticket update(Ticket ticket) {
        if (ticketRepository.existsById(ticket.getTicketId())) {
            // Get the existing ticket from the database
            Ticket existingTicket = ticketRepository.findById(ticket.getTicketId()).orElse(null);

            if (existingTicket != null) {
                // Create a builder from the existing ticket
                Ticket.Builder builder = new Ticket.Builder()
                        .setTicketId(existingTicket.getTicketId())
                        .setIssueDate(existingTicket.getIssueDate())
                        .setTicketType(existingTicket.getTicketType())
                        .setVehicle(existingTicket.getVehicle());

                // Only update the status and payment if provided
                if (ticket.getStatus() != null) {
                    builder.setStatus(ticket.getStatus());
                } else {
                    builder.setStatus(existingTicket.getStatus());
                }

                if (ticket.getPayment() != null) {
                    builder.setPayment(ticket.getPayment());
                } else {
                    builder.setPayment(existingTicket.getPayment());
                }

                return ticketRepository.save(builder.build());
            }
        }
        return null;
    }

    @Override
    public List<Ticket> getTickets() {
        return ticketRepository.findAll();
    }


    public boolean delete(Integer id) {
        if (ticketRepository.existsById(id)) {
            ticketRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // 🔹 New methods
    public List<Ticket> getTicketsByVehicleId(Integer vehicleId) {
        return ticketRepository.findByVehicle_VehicleID(vehicleId);
    }

    public List<Ticket> getTicketsByApplicantId(Integer applicantId) {
        return ticketRepository.findByVehicleApplicantUserId(applicantId);
    }

    public Ticket updateTicket(int ticketId, Ticket ticketData) {
        Optional<Ticket> optionalTicket = ticketRepository.findById(ticketId);

        if (optionalTicket.isEmpty()) {
            throw new RuntimeException("Ticket not found with ID: " + ticketId);
        }

        Ticket existingTicket = optionalTicket.get();

        // Use Builder to copy existing ticket and override only necessary fields
        Ticket updatedTicket = new Ticket.Builder()
                .copy(existingTicket)
                .setStatus(ticketData.getStatus() != null ? ticketData.getStatus() : existingTicket.getStatus())
                .setPayment(ticketData.getPayment() != null ? ticketData.getPayment() : existingTicket.getPayment())
                .build();

        // Save updated ticket
        return ticketRepository.save(updatedTicket);
    }
}


