package za.ac.cput.Service;

import za.ac.cput.Domain.User.Applicant;

import java.util.List;

public interface IApplicantService extends IService<Applicant, Integer> {
    List<Applicant> getAll();

    boolean delete(Integer id);
}
