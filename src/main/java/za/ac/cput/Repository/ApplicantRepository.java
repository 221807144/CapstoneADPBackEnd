package za.ac.cput.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import za.ac.cput.Domain.User.Applicant;

import java.util.Optional;

public interface ApplicantRepository extends JpaRepository<Applicant, Integer> {
    // Find applicant by email
    @Query("SELECT a FROM Applicant a WHERE a.contact.email = :email")
    Optional<Applicant> findByEmail(@Param("email") String email);

    // Check if email exists
    @Query("SELECT COUNT(a) > 0 FROM Applicant a WHERE a.contact.email = :email")
    boolean existsByEmail(@Param("email") String email);
}
