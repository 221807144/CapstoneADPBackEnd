package za.ac.cput.Factory.payment;

import za.ac.cput.Domain.payment.Payment;
import za.ac.cput.Domain.payment.Ticket;
import za.ac.cput.Util.Helper;

import java.time.LocalDate;

public class TicketFactory {

    public static Ticket createTicket(String ticketType) {

        if (Helper.isNullOrEmpty(ticketType)) {
            return null;
        }

        String status = "Not Paid";

        LocalDate issueDate = LocalDate.now();

        Ticket.TicketType type = Ticket.TicketType.valueOf(ticketType);

        return new Ticket.Builder()
                .setIssueDate(issueDate)
                .setStatus(status)
                .setTicketType(type)
                .build();
    }

    public static Ticket createTicket(String ticketType, Payment payment) {

        if (Helper.isNullOrEmpty(ticketType)) {
            return null;
        }

        String status = "Paid";

        LocalDate issueDate = LocalDate.now();

        Ticket.TicketType type = Ticket.TicketType.valueOf(ticketType);

        return new Ticket.Builder()
                .setIssueDate(issueDate)
                .setStatus(status)
                .setTicketType(type)
                .setPayment(payment)
                .build();
    }
}
