package za.ac.cput.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import za.ac.cput.Domain.bookings.VehicleDisc;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VehicleDiscRepository extends JpaRepository<VehicleDisc, Long> {

    // Find all vehicle discs that have already expired
    @Query("SELECT vd FROM VehicleDisc vd WHERE vd.expiryDate < :today")
    List<VehicleDisc> findExpiredDiscs(LocalDate today);
}
