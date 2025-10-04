package za.ac.cput.Controller.Registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import za.ac.cput.Domain.Registrations.Vehicle;
import za.ac.cput.Service.impl.VehicleService;


import java.time.LocalDate;
import java.util.Base64;
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

//    @PutMapping("/update")
//    public Vehicle update(@RequestBody Vehicle vehicle) {
//        return vehicleService.update(vehicle);
//    }
@PutMapping("/update")
public ResponseEntity<Vehicle> updateVehicle(
        @RequestParam("vehicleID") int vehicleID,
        @RequestParam(value = "vehicleType", required = false) String vehicleType,
        @RequestParam(value = "vehicleColor", required = false) String vehicleColor,
        @RequestParam(value = "vehicleYear", required = false) String vehicleYear,
        @RequestParam(value = "engineNumber", required = false) String engineNumber,
        @RequestParam(value = "licensePlate", required = false) String licensePlate,
        @RequestParam(value = "image", required = false) MultipartFile imageFile) {

    try {
        System.out.println("🔄 Updating vehicle ID: " + vehicleID);

        Vehicle existingVehicle = vehicleService.read(vehicleID);
        if (existingVehicle == null) {
            return ResponseEntity.notFound().build();
        }

        Vehicle.Builder builder = new Vehicle.Builder()
                .setVehicleID(existingVehicle.getVehicleID())
                .setVehicleName(existingVehicle.getVehicleName())
                .setVehicleType(vehicleType != null ? vehicleType : existingVehicle.getVehicleType())
                .setVehicleModel(existingVehicle.getVehicleModel())
                .setVehicleYear(vehicleYear != null ? vehicleYear : existingVehicle.getVehicleYear())
                .setVehicleColor(vehicleColor != null ? vehicleColor : existingVehicle.getVehicleColor())
                .setLicensePlate(licensePlate != null ? licensePlate : existingVehicle.getLicensePlate())
                .setEngineNumber(engineNumber != null ? engineNumber : existingVehicle.getEngineNumber())
                .setVehicleDisc(existingVehicle.getVehicleDisc())
                .setTicket(existingVehicle.getTicket())
                .setApplicant(existingVehicle.getApplicant());

        // Handle image upload - FIX: Add data URL prefix
        if (imageFile != null && !imageFile.isEmpty()) {
            String base64Image = "data:" + imageFile.getContentType() + ";base64," +
                    Base64.getEncoder().encodeToString(imageFile.getBytes());
            builder.setVehicleImage(base64Image);
            System.out.println("✅ Image converted to Base64 with prefix");
        } else {
            builder.setVehicleImage(existingVehicle.getVehicleImage());
            System.out.println("🔄 Using existing image");
        }

        Vehicle updatedVehicle = vehicleService.update(builder.build());
        System.out.println("✅ Vehicle updated successfully");
        return ResponseEntity.ok(updatedVehicle);

    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
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
