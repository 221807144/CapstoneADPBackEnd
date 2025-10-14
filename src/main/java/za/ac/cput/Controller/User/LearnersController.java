package za.ac.cput.Controller.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.Domain.User.Learners;
import za.ac.cput.Service.impl.LearnersService;

import java.util.List;

@RestController
@RequestMapping("/learners")
public class LearnersController {

    private final LearnersService learnersService; // Field name should be lowercase

    @Autowired
    public LearnersController(LearnersService learnersService) {
        this.learnersService = learnersService; // Constructor parameter should match
    }

    @PostMapping("/create")
    public Learners create(@RequestBody Learners learners) {
        return learnersService.create(learners); // ✅ FIXED: lowercase 'l'
    }

    @GetMapping("/read/{id}")
    public Learners read(@PathVariable String id) {
        return learnersService.read(id); // ✅ FIXED: lowercase 'l'
    }

    @PutMapping("/update")
    public Learners update(@RequestBody Learners learners) {
        return learnersService.update(learners); // ✅ FIXED: lowercase 'l'
    }

    @GetMapping("/getAll")
    public List<Learners> getAll() {
        return learnersService.getAll(); // ✅ FIXED: lowercase 'l'
    }
}