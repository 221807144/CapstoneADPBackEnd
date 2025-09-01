package za.ac.cput.Service;

import org.springframework.stereotype.Service;
import za.ac.cput.Domain.bookings.Bookings;

import java.util.List;
@Service
public interface IBookingsService extends IService<Bookings, Long> {


    List<Bookings> getAll();

}
