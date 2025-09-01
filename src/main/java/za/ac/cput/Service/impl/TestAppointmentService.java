package za.ac.cput.Service.impl;

import org.springframework.stereotype.Service;
import za.ac.cput.Domain.User.Applicant;
import za.ac.cput.Domain.bookings.TestAppointment;
import za.ac.cput.Repository.TestAppointmentRepository;
import za.ac.cput.Service.ITestAppointmentService;

import java.util.List;
import java.util.Optional;

@Service
public class TestAppointmentService implements ITestAppointmentService {

    private final TestAppointmentRepository testAppointmentRepository;

    public TestAppointmentService(TestAppointmentRepository testAppointmentRepository) {
        this.testAppointmentRepository = testAppointmentRepository;
    }

    @Override
    public TestAppointment create(TestAppointment testAppointment) { //updated
        return testAppointmentRepository.save(testAppointment);
    }

    @Override
    public TestAppointment read(Long id) {
        return testAppointmentRepository.findById(id).orElse(null);
    }

    @Override
    public TestAppointment update(TestAppointment testAppointment) { // updates
        return testAppointmentRepository.save(testAppointment);
    }
//    @Override
//    public void delete(Long id) {
//        testAppointmentRepository.deleteById(id);
//
//    }

    @Override
    public Optional<TestAppointment> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<TestAppointment> getAll() {
        return testAppointmentRepository.findAll();
    }
    @Override
    public List<TestAppointment> getByApplicantId(Long applicantId) {
        return testAppointmentRepository.findByApplicantUserId(applicantId);
    }


}