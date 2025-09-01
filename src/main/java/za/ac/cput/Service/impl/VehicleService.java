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
//    @Deprecated
//    @Override
//    public void delete(Integer integer) {
//    this.vehicleRepository.deleteById(integer);
//
//    }
    @Override
    public List<Vehicle> getAll() {
        return this.vehicleRepository.findAll();
    }
    public List<Vehicle> getExpiredVehicles() {
        return vehicleRepository.findExpiredVehicles();
    }

//    @Override
//    public List<Vehicle> getExpiredByApplicant(int applicantId) {
//        LocalDate today = LocalDate.now();
//        return vehicleRepository.findExpiredByApplicant(applicantId, today);
//    }
    public List<Vehicle> getExpiredByApplicant(int applicantId, LocalDate today) {
        List<Vehicle> result = vehicleRepository.findExpiredByApplicant(applicantId, today);
        System.out.println("Expired vehicles for applicant " + applicantId + ": " + result.size());
        result.forEach(v -> System.out.println("Vehicle: " + v.getLicensePlate() +
                ", Expiry: " + v.getVehicleDisc().getExpiryDate()));
        return result;
    }

    @Override
    public void delete(Integer id) {
        vehicleRepository.deleteById(id);
    }

    public void delete(int vehicleID) {
        vehicleRepository.deleteById(vehicleID);
    }


    public List<Vehicle> getVehiclesByApplicant(int applicantId) {
        return vehicleRepository.findByApplicant_UserId(applicantId);
    }




}
