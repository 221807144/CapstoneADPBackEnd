package za.ac.cput.Service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.Domain.Registrations.Vehicle;
import za.ac.cput.Domain.User.Applicant;
import za.ac.cput.Repository.ApplicantRepository;
import za.ac.cput.Repository.VehicleRepository;
import za.ac.cput.Service.IVehicleService;

import java.util.List;

@Service
public class VehicleService implements IVehicleService {

    private VehicleRepository vehicleRepository;

    private  ApplicantRepository applicantRepository;
    @Autowired
    public VehicleService(VehicleRepository vehicleRepository, ApplicantRepository applicantRepository) {
        this.vehicleRepository = vehicleRepository;
        this.applicantRepository = applicantRepository;
    }

    @Override
    public Vehicle create(Vehicle vehicle) {
        // 1️⃣ Fetch the actual Applicant entity from DB
        Applicant applicant = applicantRepository.findById(vehicle.getApplicant().getUserId())
                .orElseThrow(() -> new RuntimeException("Applicant not found"));

        // 2️⃣ Build a new Vehicle object with the Builder
        Vehicle vehicleToSave = new Vehicle.Builder()
                .setVehicleName(vehicle.getVehicleName())
                .setVehicleType(vehicle.getVehicleType())
                .setVehicleModel(vehicle.getVehicleModel())
                .setVehicleYear(vehicle.getVehicleYear())
                .setVehicleColor(vehicle.getVehicleColor())
                .setLicensePlate(vehicle.getLicensePlate())
                .setEngineNumber(vehicle.getEngineNumber())
                .setVehicleDisc(vehicle.getVehicleDisc())
                .setPayment(vehicle.getPayment())
                .setApplicant(applicant) // ✅ set managed applicant entity
                .build();

        // 3️⃣ Save to DB
        return vehicleRepository.save(vehicleToSave);
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
    @Transactional
    public boolean delete(int vehicleID) {
        try {
            if (vehicleRepository.existsById(vehicleID)) {
                vehicleRepository.deleteById(vehicleID);
                return true;
            }
            return false;
        } catch (Exception e) {
            System.err.println("Error deleting vehicle: " + e.getMessage());
            return false;
        }
    }


//    @Override
//    public void delete(Integer id) {
//        if(vehicleRepository.existsById(id)) {
//            vehicleRepository.deleteById(id);
//        } else {
//            throw new RuntimeException("Vehicle with ID " + id + " not found");
//        }
//    }



    public List<Vehicle> getVehiclesByApplicant(int applicantId) {
        return vehicleRepository.findByApplicant_UserId(applicantId);
    }

    // made changes
    public List<Vehicle> getExpiredVehiclesForUser(int userId) {
        return vehicleRepository.findExpiredVehiclesByUser(userId);
    }



}
