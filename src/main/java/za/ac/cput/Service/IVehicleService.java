package za.ac.cput.Service;

import za.ac.cput.Domain.Registrations.Vehicle;

import java.util.List;

public interface IVehicleService extends IService <Vehicle, Integer> {
    List<Vehicle> getAll();
    List<Vehicle> getExpiredVehicles();
//    List<Vehicle> findByApplicant_UserId(int userId);
//List<Vehicle> getExpiredByApplicant(int userId);
    void delete(Integer id);

}
