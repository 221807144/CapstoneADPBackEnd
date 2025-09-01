package za.ac.cput.Service.impl;

import org.springframework.stereotype.Service;
import za.ac.cput.Domain.User.License;
import za.ac.cput.Repository.LicenseRepository;
import za.ac.cput.Service.ILicenseService;

import java.util.List;

@Service
public class LicenseService implements ILicenseService {

    private final LicenseRepository repository;

    public LicenseService(LicenseRepository licenseRepository) {
        this.repository = licenseRepository;
    }

    @Override
    public License create(License license) {
        return this.repository.save(license);
    }

    @Override
    public License read(Long id) {
        return this.repository.findById(id).orElse(null);
    }

    @Override
    public License update(License license) {
        return this.repository.save(license);
    }



    @Override
    public List<License> getAll() {
        return repository.findAll();
    }

//    @Override
//    public void delete(Long id) {
//        this.repository.deleteById(id);
//    }
}