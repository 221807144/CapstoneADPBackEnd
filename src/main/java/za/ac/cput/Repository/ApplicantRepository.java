package za.ac.cput.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.ac.cput.Domain.User.Applicant;

public interface ApplicantRepository extends JpaRepository<Applicant, Integer> {
}
