package za.ac.cput.Service;

import za.ac.cput.Domain.Registrations.Vehicle;
import za.ac.cput.Domain.bookings.VehicleDisc;

import java.util.List;

public interface IVehicleService extends IService <Vehicle, Integer> {
    List<Vehicle> getAll();

   boolean delete(int vehicleID);

    //boolean delete(Integer id);

    List<Vehicle> getExpiredVehiclesForUser(int userId);
}
