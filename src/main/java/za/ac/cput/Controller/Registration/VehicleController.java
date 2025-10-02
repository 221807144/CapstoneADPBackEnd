package za.ac.cput.Controller.Registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.Domain.Registrations.Vehicle;
import za.ac.cput.Service.impl.VehicleService;


import java.time.LocalDate;
import java.util.List;
@CrossOrigin(origins = "http://localhost:3000") // <-- allow React app
@RestController
@RequestMapping("/vehicle")

public class VehicleController {
    private VehicleService vehicleService;

    @Autowired
    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping("/create")
    public Vehicle create(@RequestBody Vehicle vehicle) {
        return vehicleService.create(vehicle);
    }
//    public ResponseEntity<Vehicle> createVehicle(@RequestBody Vehicle vehicle) {
//        Vehicle createdVehicle = vehicleService.create(vehicle);
//        return ResponseEntity.ok(createdVehicle);
//    }


    @GetMapping("/read/{vehicleID}")
    public Vehicle read(@PathVariable int vehicleID) {
        return vehicleService.read(vehicleID);
    }

    @PutMapping("/update")
    public Vehicle update(@RequestBody Vehicle vehicle) {
        return vehicleService.update(vehicle);
    }

//    @DeleteMapping("/delete/{vehicleID}")
//    public Vehicle delete(@PathVariable int vehicleID) {
//      vehicleService.delete(vehicleID);
//        return null;
//    }
//
@DeleteMapping("/delete/{vehicleID}")
public ResponseEntity<String> delete(@PathVariable int vehicleID) {
    boolean deleted = vehicleService.delete(vehicleID);

    if (deleted) {
        return ResponseEntity.ok("Vehicle deleted successfully");
    } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Vehicle not found, could not delete");
    }
}




//    @DeleteMapping("/delete/{vehicleID}")
//    public ResponseEntity<String> deleteVehicle(@PathVariable int vehicleID) {
//        vehicleService.delete(vehicleID);
//        return null;
//    }

    @GetMapping("/getAll")
    public List<Vehicle> getAll() {
        return vehicleService.getAll();
    }

    // VehicleController.java
    @GetMapping("/applicant/{applicantId}")
    public ResponseEntity<List<Vehicle>> getVehiclesByApplicant(@PathVariable int applicantId) {
        List<Vehicle> vehicles = vehicleService.getVehiclesByApplicant(applicantId);
        return ResponseEntity.ok(vehicles);
    }

// GET /capstone/vehicles/expired/{userId} - made a change
@GetMapping("/expired/{userId}")
public ResponseEntity<List<Vehicle>> getExpiredVehiclesByUser(@PathVariable int userId) {
    List<Vehicle> expiredVehicles = vehicleService.getExpiredVehiclesForUser(userId);
    return ResponseEntity.ok(expiredVehicles);
}

}
