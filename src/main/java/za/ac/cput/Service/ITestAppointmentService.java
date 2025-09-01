package za.ac.cput.Service;

import za.ac.cput.Domain.bookings.TestAppointment;

import java.util.List;
import java.util.Optional;

public interface ITestAppointmentService extends IService<TestAppointment, Long> {
//    List<TestAppointment> findByTestId(Long testAppointmentId);

    Optional<TestAppointment> findById(Long id);

    List<TestAppointment> getAll();

    List<TestAppointment> getByApplicantId(Long applicantId);
    //TestAppointment create(TestAppointment testAppointment);
    //TestAppointment read(Integer id);
    //TestAppointment update(TestAppointment testAppointment);
//    void delete(Integer id);



}