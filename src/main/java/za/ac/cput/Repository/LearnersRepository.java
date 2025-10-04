package za.ac.cput.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.cput.Domain.User.Learners;

@Repository
public interface LearnersRepository extends JpaRepository<Learners, String> {

}
