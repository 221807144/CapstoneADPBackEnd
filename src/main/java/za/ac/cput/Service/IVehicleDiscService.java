package za.ac.cput.Service;

import za.ac.cput.Domain.bookings.VehicleDisc;

import java.util.List;

public interface IVehicleDiscService extends IService <VehicleDisc, Long> {
    List<VehicleDisc>getExpiredDiscs();
    List<VehicleDisc> getAll();
//    List<VehicleDisc> getExpiredDiscsByUser(Long userId);

}
