package za.ac.cput.Service;

import za.ac.cput.Domain.User.Learners;

import java.util.List;

public interface ILearnersService extends IService<Learners, String> {

    List<Learners> getAll();
}
