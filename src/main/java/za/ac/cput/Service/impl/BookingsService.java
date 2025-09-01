package za.ac.cput.Service.impl;

import org.springframework.stereotype.Service;
import za.ac.cput.Domain.bookings.Bookings;
import za.ac.cput.Repository.BookingsRepository;
import za.ac.cput.Repository.TestAppointmentRepository;
import za.ac.cput.Repository.VehicleDiscRepository;
import za.ac.cput.Service.IBookingsService;

import java.util.List;
@Service
public class BookingsService implements IBookingsService {

    private TestAppointmentRepository testAppointmentRepository;
    private BookingsRepository repository;
    private VehicleDiscRepository vehicleDiscRepository;


    public BookingsService(BookingsRepository bookingsRepository) {
        this.repository = bookingsRepository;
    }

    @Override
    public Bookings create(Bookings bookings) {
        return this.repository.save(bookings);
    }

    @Override
    public Bookings read(Long aLong) {
        return this.repository.findById(aLong)
                .orElse(null);
    }

    @Override
    public Bookings update(Bookings bookings) {
        return this.repository.save(bookings);
    }

//    @Override
//    public void delete(Long aLong) {
//
//    }
    @Override
    public List<Bookings> getAll() {
        return List.of();
    }


}
