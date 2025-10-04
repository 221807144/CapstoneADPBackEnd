package za.ac.cput.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import za.ac.cput.Domain.payment.Ticket;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {

    // All tickets linked to a specific vehicle
    List<Ticket> findByVehicle_VehicleID(Integer vehicleID);

    @Query("SELECT t FROM Ticket t WHERE t.vehicle.applicant.userId = :userId")
    List<Ticket> findByVehicleApplicantUserId(@Param("userId") Integer userId);

}
