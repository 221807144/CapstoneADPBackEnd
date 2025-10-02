package za.ac.cput.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import za.ac.cput.Domain.Registrations.Vehicle;
import za.ac.cput.Domain.bookings.VehicleDisc;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {

    List<Vehicle> findByApplicant_UserId(int userId);

//fetch all vehicles with expired disc

    @Query("SELECT v FROM Vehicle v " +
            "WHERE v.applicant.userId = :userId " +
            "AND v.vehicleDisc IS NOT NULL " +
            "AND v.vehicleDisc.expiryDate < CURRENT_DATE")
    List<Vehicle> findExpiredVehiclesByUser(@Param("userId") int userId);


}
