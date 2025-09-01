package za.ac.cput.Service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.Domain.Registrations.Registration;
import za.ac.cput.Repository.RegistrationRepository;
import za.ac.cput.Service.IRegistrationService;

import java.util.List;//
@Service
public class RegistrationService implements IRegistrationService {
    @Autowired
    private RegistrationRepository repository;
    @Autowired
    public RegistrationService(RegistrationRepository repository) {
        this.repository = repository;
    }
    @Override
    public Registration create(Registration registration) {
        return this.repository.save(registration);
    }
    //  Sinokholo Singazi
    @Override
    public Registration read(Integer integer) {
        return this.repository.findById(integer).orElse(null);
    }

    @Override
    public Registration update(Registration registration) {
        return this.repository.save(registration);
    }
//    @Override
//    public void delete(Integer integer) {
//        this.repository.deleteById(integer);
//    }

    @Override
    public List<Registration> getAll() {
        return this.repository.findAll();
    }


    }

