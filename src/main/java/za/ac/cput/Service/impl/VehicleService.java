package za.ac.cput.Service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.Domain.Registrations.Vehicle;
import za.ac.cput.Repository.VehicleRepository;
import za.ac.cput.Service.IVehicleService;

import java.time.LocalDate;
import java.util.List;

@Service
public class VehicleService implements IVehicleService {

    private VehicleRepository vehicleRepository;
@Autowired
    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public Vehicle create(Vehicle vehicle) {
        return this.vehicleRepository.save(vehicle);
    }

    @Override
    public Vehicle read(Integer integer) {
        return this.vehicleRepository.findById(integer).orElse(null);


    }

    @Override
    public Vehicle update(Vehicle vehicle) {
        return this.vehicleRepository.save(vehicle);

    }
////    @Deprecated
//    @Override
//    public void delete(Integer integer) {
//    this.vehicleRepository.deleteById(integer);
//    }

//@Override
//public void delete(Integer vehicleId) {
//    try {
//        // Check if vehicle exists first (optional, but safer)
//        if (!vehicleRepository.existsById(vehicleId)) {
//            throw new IllegalArgumentException("Vehicle with ID " + vehicleId + " does not exist");
//        }
//        // Attempt deletion
//        vehicleRepository.deleteById(vehicleId);
//    } catch (Exception e) {
//        // Wrap any exception with a runtime exception with message
//        throw new RuntimeException("Failed to delete vehicle with ID " + vehicleId + ": " + e.getMessage(), e);
//    }
//}

    @Override
    public List<Vehicle> getAll() {
        return this.vehicleRepository.findAll();
    }

    @Override
    public void delete(Integer id) {
        if(vehicleRepository.existsById(id)) {
            vehicleRepository.deleteById(id);
        } else {
            throw new RuntimeException("Vehicle with ID " + id + " not found");
        }
    }

    public List<Vehicle> getVehiclesByApplicant(int applicantId) {
        return vehicleRepository.findByApplicant_UserId(applicantId);
    }

    // made changes
    public List<Vehicle> getExpiredVehiclesForUser(int userId) {
        return vehicleRepository.findExpiredVehiclesByUser(userId);
    }



}
