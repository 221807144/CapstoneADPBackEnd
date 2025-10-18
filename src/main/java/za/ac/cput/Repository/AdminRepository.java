package za.ac.cput.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.ac.cput.Domain.User.Admin;

public interface AdminRepository extends JpaRepository<Admin, Integer> {

}
