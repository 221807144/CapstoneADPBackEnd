package za.ac.cput.Service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.Domain.User.Learners;
import za.ac.cput.Repository.LearnersRepository;
import za.ac.cput.Service.ILearnersService;

import java.util.List;
@Service
public class LearnersService implements ILearnersService {

    private final LearnersRepository repository;
    @Autowired
    public LearnersService(LearnersRepository repository) {
        this.repository = repository;
    }
    @Override
    public Learners create(Learners learners) {
        return this.repository.save(learners);
    }
    @Override
    public Learners read(String s) {
        return this.repository.findById(s).orElse(null);
    }
    @Override
    public Learners update(Learners learners) {
        return this.repository.save(learners);
    }
    @Override
    public List<Learners> getAll() {
        return this.repository.findAll();
    }
}
