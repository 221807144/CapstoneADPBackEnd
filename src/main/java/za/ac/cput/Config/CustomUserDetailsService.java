package za.ac.cput.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import za.ac.cput.Domain.User.Applicant;
import za.ac.cput.Domain.User.Admin;
import za.ac.cput.Service.impl.ApplicantService;
import za.ac.cput.Service.impl.AdminService;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private ApplicantService applicantService;

    @Autowired
    private AdminService adminService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("🔍 CustomUserDetailsService searching for user with email: " + email);

        // Search through all applicants
        List<Applicant> allApplicants = applicantService.getAll();
        System.out.println("📊 Total applicants in database: " + allApplicants.size());
        allApplicants.forEach(app -> System.out.println("   - " + (app.getContact() != null ? app.getContact().getEmail() : "NULL CONTACT")));

        Applicant applicant = allApplicants.stream()
                .filter(app -> app.getContact() != null &&
                        app.getContact().getEmail() != null &&
                        app.getContact().getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);

        if (applicant != null) {
            System.out.println("✅ Found applicant: " + applicant.getContact().getEmail());
            System.out.println("🔑 Applicant password: " + applicant.getPassword());
            return new CustomUserDetails(
                    applicant.getContact().getEmail(),
                    applicant.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_APPLICANT")),
                    applicant.getUserId(),
                    "APPLICANT"
            );
        }

        // Search through all admins
        List<Admin> allAdmins = adminService.getAllAdmins();
        System.out.println("📊 Total admins in database: " + allAdmins.size());
        allAdmins.forEach(adm -> System.out.println("   - " + (adm.getContact() != null ? adm.getContact().getEmail() : "NULL CONTACT")));

        Admin admin = allAdmins.stream()
                .filter(adm -> adm.getContact() != null &&
                        adm.getContact().getEmail() != null &&
                        adm.getContact().getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);

        if (admin != null) {
            System.out.println("✅ Found admin: " + admin.getContact().getEmail());
            System.out.println("🔑 Admin password: " + admin.getPassword());
            return new CustomUserDetails(
                    admin.getContact().getEmail(),
                    admin.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")),
                    admin.getUserId(),
                    "ADMIN"
            );
        }

        System.out.println("❌ User not found with email: " + email);
        throw new UsernameNotFoundException("User not found with email: " + email);
    }

    // Custom UserDetails implementation
    public static class CustomUserDetails extends org.springframework.security.core.userdetails.User {
        private final Integer userId;
        private final String userType;

        public CustomUserDetails(String username, String password,
                                 Collection<? extends GrantedAuthority> authorities,
                                 Integer userId, String userType) {
            super(username, password, authorities);
            this.userId = userId;
            this.userType = userType;
        }

        public Integer getUserId() {
            return userId;
        }

        public String getUserType() {
            return userType;
        }
    }
}