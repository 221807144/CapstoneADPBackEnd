package za.ac.cput.Controller.Booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
        import za.ac.cput.Domain.bookings.Bookings;
import za.ac.cput.Service.impl.BookingsService;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingsController {

    private final BookingsService bookingsService;

    @Autowired
    public BookingsController(BookingsService bookingsService) {
        this.bookingsService = bookingsService;
    }

    @PostMapping("/create")
    public ResponseEntity<Bookings> create(@RequestBody Bookings booking) {
        Bookings created = bookingsService.create(booking);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<Bookings> read(@PathVariable Long id) {
        Bookings booking = bookingsService.read(id);
        return ResponseEntity.ok(booking);
    }

    @PutMapping("/update")
    public ResponseEntity<Bookings> update(@RequestBody Bookings booking) {
        Bookings updated = bookingsService.update(booking);
        return ResponseEntity.ok(updated);
    }

//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<Void> delete(@PathVariable Long id) {
//        boolean deleted = bookingsService.delete(id);
//        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
//    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Bookings>> getAll() {
        return ResponseEntity.ok(bookingsService.getAll());
    }

    @GetMapping("/byType/{booktype}")
    public List<Bookings> getBookingsByType(@PathVariable Bookings.Booktype booktype) {
        return bookingsService.getAll().stream()
                .filter(booking -> booking.getBooktype() == booktype)
                .toList();
    }

}
