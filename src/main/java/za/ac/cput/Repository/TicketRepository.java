package za.ac.cput.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.cput.Domain.payment.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket,Integer> {
}
