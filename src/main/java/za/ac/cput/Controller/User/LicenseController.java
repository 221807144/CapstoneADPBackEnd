package za.ac.cput.Controller.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
        import za.ac.cput.Domain.User.License;
import za.ac.cput.Service.impl.LicenseService;

import java.util.List;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/license")
public class LicenseController {
    private LicenseService licenseService;

    @Autowired
    public LicenseController(LicenseService licenseService) {
        this.licenseService = licenseService;
    }

    @PostMapping("/create")
    public License create(@RequestBody License license) {
        return licenseService.create(license); // changed
    }

    @GetMapping("/read/{licenseId}")
    public License read(@PathVariable Long licenseId) {
        return licenseService.read(licenseId);
    }

    @PutMapping("/update")
    public License update(@RequestBody License license) {
        return licenseService.update(license);
    }

//    @DeleteMapping("/delete/{licenseId}")
//    public boolean delete(@PathVariable Long licenseId) {
//        return licenseService.delete(licenseId);
//    }

    @GetMapping("/getAll")
    public List<License> getAll() {
        return licenseService.getAll();
    }
}