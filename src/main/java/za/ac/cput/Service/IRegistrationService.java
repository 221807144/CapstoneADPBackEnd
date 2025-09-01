package za.ac.cput.Service;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import za.ac.cput.Domain.Registrations.Registration;

import java.util.List;
@Service
public interface IRegistrationService extends IService<Registration, Integer> {
    List<Registration> getAll();
    //void delete(Integer id);
}
