package za.ac.cput.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import za.ac.cput.Domain.Registrations.Vehicle;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {
    @Query("SELECT v FROM Vehicle v WHERE v.vehicleDisc IS NOT NULL AND v.vehicleDisc.expiryDate <= CURRENT_DATE")
    List<Vehicle> findExpiredVehicles();

    @Query("SELECT v FROM Vehicle v WHERE v.applicant.userId = :applicantId AND v.vehicleDisc.expiryDate <= :today")
    List<Vehicle> findExpiredByApplicant(@Param("applicantId") int applicantId, @Param("today") LocalDate today);
    List<Vehicle> findByApplicant_UserId(int userId);

}
