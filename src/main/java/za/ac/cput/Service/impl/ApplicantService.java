package za.ac.cput.Service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.Domain.User.Applicant;
import za.ac.cput.Repository.ApplicantRepository;
import za.ac.cput.Service.IApplicantService;

import java.util.List;
import java.util.Optional;

@Service
public class ApplicantService implements IApplicantService {

    private final ApplicantRepository applicantRepository;

    @Autowired
    public ApplicantService(ApplicantRepository applicantRepository) {
        this.applicantRepository = applicantRepository;
    }

    @Override
    public Applicant create(Applicant applicant) {
        return applicantRepository.save(applicant);
    }

    @Override
    public Applicant read(Integer id) {
        Optional<Applicant> applicant = applicantRepository.findById(id);
        return applicant.orElse(null);
    }

    @Override
    public Applicant update(Applicant applicant) {
        if (applicantRepository.existsById(applicant.getUserId())) {
            return applicantRepository.save(applicant);
        }
        return null;
    }

    @Override
    public boolean delete(Integer id) {
        if (applicantRepository.existsById(id)) {
            applicantRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Applicant> getAll() {
        return applicantRepository.findAll();
    }
}
