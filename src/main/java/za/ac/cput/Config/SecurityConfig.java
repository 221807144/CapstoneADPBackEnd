package za.ac.cput.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> {})
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authz -> authz
                        // Allow public access to registration and login
                        .requestMatchers("/applicants/create", "/applicants/login").permitAll()
                        .requestMatchers("/admins/create", "/admins/login").permitAll()
//                        .requestMatchers("/admins/").hasAuthority("ROLE_ADMIN")
//                        .requestMatchers("/applicants/").hasAuthority("ROLE_APPLICANT")

                        // Protect all other endpoints
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form.disable()) // Disable form login for REST API
                .httpBasic(httpBasic -> httpBasic.disable()); // Disable basic auth

        return http.build();
    }
}
