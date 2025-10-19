package za.ac.cput.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import za.ac.cput.Domain.User.Admin;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
    // Find admin by email
    @Query("SELECT a FROM Admin a WHERE a.contact.email = :email")
    Optional<Admin> findByEmail(@Param("email") String email);

    // Check if email exists
    @Query("SELECT COUNT(a) > 0 FROM Admin a WHERE a.contact.email = :email")
    boolean existsByEmail(@Param("email") String email);
}
