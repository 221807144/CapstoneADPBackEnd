package za.ac.cput.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints - no authentication required
                        .requestMatchers(
                                "/capstone/applicants/create",
                                "/capstone/applicants/login",
                                "/capstone/applicants/test-public",
                                "/capstone/applicants/test-token",
                                "/capstone/applicants/validate-token",
                                "/capstone/admins/create",
                                "/capstone/admins/login",
                                "/capstone/admins/test-token",
                                "/capstone/admins/validate-token",
                                "/applicants/create",
                                "/applicants/login",
                                "/applicants/test-public",
                                "/applicants/test-token",
                                "/applicants/validate-token",
                                "/admins/create",
                                "/admins/login",
                                "/admins/test-token",
                                "/admins/validate-token"
                        ).permitAll()

                        // Applicant endpoints - require APPLICANT or ADMIN role
                        .requestMatchers("/capstone/applicants/**", "/applicants/**").hasAnyRole("APPLICANT", "ADMIN")

                        // Admin endpoints - require ADMIN role only
                        .requestMatchers("/capstone/admins/**", "/admins/**").hasRole("ADMIN")

                        // Any other request needs authentication
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // ✅ CORS configuration (keep as is)
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(List.of("*"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}