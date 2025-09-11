package za.ac.cput.Service;

import za.ac.cput.Domain.Registrations.Vehicle;
import za.ac.cput.Domain.User.Admin;
import za.ac.cput.Domain.User.Applicant;
import za.ac.cput.Domain.bookings.Bookings;
import za.ac.cput.Domain.bookings.TestAppointment;
import za.ac.cput.Domain.bookings.VehicleDisc;
import za.ac.cput.Domain.payment.Payment;
import za.ac.cput.Domain.payment.Ticket;
import za.ac.cput.Domain.Registrations.Registration;

import java.util.List;

public interface IAdminService extends IService<Admin, Integer> {

    List<Admin> getAllAdmins();

    List<Payment> getPayments();

    List<Bookings> getBookings();

    List<Applicant> getAllApplicants();

    List<Registration> getRegistration();

    List<TestAppointment> getTestAppointments();
    List<Vehicle> getVehicles();

    List<VehicleDisc> getVehicleDiscs();

    List<Ticket> getTickets();

    boolean deleteAdmin(Integer id);
    boolean deleteApplicant(Integer id);
    boolean deleteBooking(Long id);
    boolean deleteTestAppointment(Long id);
    boolean deleteVehicleDisc(Long id);
    boolean deletePayment(Integer id);
    boolean deleteTicket(Integer id);
}