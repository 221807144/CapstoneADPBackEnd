package za.ac.cput.Controller.Booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.Domain.bookings.VehicleDisc;
import za.ac.cput.Service.impl.VehicleDiscService;

import java.util.List;

@RestController
@RequestMapping("/vehicledisc")
@CrossOrigin(origins = "http://localhost:3000") // <-- allow React app
public class VehicleDiscController {
    private VehicleDiscService vehicleDiscService;

    @Autowired
    public VehicleDiscController(VehicleDiscService vehicleDiscService) {
        this.vehicleDiscService = vehicleDiscService;
    }

    @PostMapping("/create")
    public VehicleDisc create(@RequestBody VehicleDisc vehicleDisc) {
        return vehicleDiscService.create(vehicleDisc);
    }

    @GetMapping("/read/{discId}")

    public VehicleDisc read(@PathVariable Long discId) {
        return vehicleDiscService.read(discId);
    }

    @PutMapping("/update")
    public VehicleDisc update(@RequestBody VehicleDisc vehicleDisc) {
        return vehicleDiscService.update(vehicleDisc);
    }
//    @DeleteMapping("/delete/{discId}")
//    public void delete(@PathVariable Long discId) {
//        vehicleDiscService.delete(discId);
//    }
    @GetMapping("/getAll")
    public List<VehicleDisc> getAll() {
        return vehicleDiscService.getAll();
    }

    @GetMapping("/expired")
    public List<VehicleDisc> getExpiredDiscs() {
        return vehicleDiscService.getExpiredDiscs();
    }

}
