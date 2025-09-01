package za.ac.cput.Service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.Domain.bookings.VehicleDisc;
import za.ac.cput.Repository.VehicleDiscRepository;
import za.ac.cput.Service.IVehicleDiscService;

import java.time.LocalDate;
import java.util.List;
@Service
public class VehicleDiscService implements IVehicleDiscService {

   private VehicleDiscRepository repository;

@Autowired
    public VehicleDiscService(VehicleDiscRepository repository) {
        this.repository = repository;
    }

    @Override
    public VehicleDisc create(VehicleDisc vehicleDisc) {
        return this.repository.save(vehicleDisc);
    }

    @Override
    public VehicleDisc read(Long id) {
        return this.repository.findById(id).orElse(null);
    }

    @Override
    public VehicleDisc update(VehicleDisc vehicleDisc) {
        return this.repository.save(vehicleDisc);
    }

//    @Override
//    public void delete(Long id) {
//    this.repository.deleteById(id);
//
//    }

    @Override
    public List<VehicleDisc> getAll() {
        return this.repository.findAll();
    }

    // New method to get all expired discs
    public List<VehicleDisc> getExpiredDiscs() {
        return this.repository.findExpiredDiscs(LocalDate.now());
    }
}
