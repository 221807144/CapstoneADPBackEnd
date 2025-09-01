package za.ac.cput.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.ac.cput.Domain.User.Applicant;
import za.ac.cput.Domain.bookings.TestAppointment;

import java.util.List;

public interface TestAppointmentRepository extends JpaRepository<TestAppointment, Long> {

    List<TestAppointment> findByApplicantUserId(Long userId);


    //TestAppointment findByTestAppointmentId(Long testAppointmentId); // Corrected method name

//   TestAppointment findByTestId(Long testAppointmentId);
}