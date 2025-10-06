package za.ac.cput.Controller.Booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.Domain.User.Applicant;
import za.ac.cput.Domain.bookings.TestAppointment;
import za.ac.cput.Service.impl.ApplicantService;
import za.ac.cput.Service.impl.PaymentService;
import za.ac.cput.Service.impl.TestAppointmentService;

import java.util.List;

@RestController
@RequestMapping("/test-appointments")
@CrossOrigin(origins = "http://localhost:3000") //updated
public class TestAppointmentController {

    private TestAppointmentService testAppointmentService;
    private ApplicantService applicantService;
    private PaymentService paymentService;
@Autowired
    public TestAppointmentController(TestAppointmentService testAppointmentService, ApplicantService applicantService, PaymentService paymentService) {
        this.testAppointmentService = testAppointmentService;
        this.applicantService = applicantService;
        this.paymentService = paymentService;
    }

    @PostMapping("/create")
    public TestAppointment create(@RequestBody TestAppointment testAppointment) {
        return testAppointmentService.create(testAppointment);
    }

    @GetMapping("/read/{id}")
    public TestAppointment read(@PathVariable Long id) {
        return testAppointmentService.read(id);
    }

    @PutMapping("/update")
    public TestAppointment update(@RequestBody TestAppointment testAppointment) {
        return testAppointmentService.update(testAppointment);
    }

//    @DeleteMapping("/delete/{id}")
//    public boolean delete(@PathVariable Long id) {
//        return testAppointmentService.delete(id);
//    }

    @GetMapping("/getAll")
    public List<TestAppointment> getAll() {
        return testAppointmentService.getAll();
    }

    @GetMapping("/by-applicant/{applicantId}")
    public List<TestAppointment> getByApplicant(@PathVariable Long applicantId) {
        return testAppointmentService.getByApplicantId(applicantId);
    }

}