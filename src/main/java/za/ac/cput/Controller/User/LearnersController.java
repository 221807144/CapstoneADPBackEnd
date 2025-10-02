package za.ac.cput.Controller.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.Domain.User.Learners;
import za.ac.cput.Service.impl.LearnersService;


import java.util.List;

@RestController
@RequestMapping("/learners")
public class LearnersController { //this one

    private final LearnersService learnersService;
    @Autowired
    public LearnersController(LearnersService learnersService) {
        this.learnersService = learnersService;
    }

    @PostMapping("/create")
    public Learners create(@RequestBody Learners learners) {
        return learnersService.create(learners);
    }

    @GetMapping("/read/{id}")
    public Learners read(@PathVariable String id) {
        return learnersService.read(id);
    }

    @PutMapping("/update")
    public Learners update(@RequestBody Learners learners) {
        return learnersService.update(learners);
    }

//    @DeleteMapping("/delete/{id}")
//    public boolean delete(@PathVariable String id) {
//        return learnersService.delete(id);
//    }

    @GetMapping("/getAll")
    public List<Learners> getAll() {
        return learnersService.getAll();
    }
}

