package za.ac.cput.Config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import za.ac.cput.Service.jwt.TokenUtil;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    // List of public endpoints that don't require authentication
    // List of public endpoints that don't require authentication
    private final List<String> PUBLIC_ENDPOINTS = Arrays.asList(
            "/applicants/create",
            "/applicants/login",
            "/applicants/verify-email-password-reset",  // ✅
            "/applicants/reset-password",               // ✅
            "/applicants/validate-token",
            "/applicants/test-token",
            "/applicants/test-public",

            // ✅ ADD DOCUMENT ENDPOINTS HERE
            "/applicant/documents/",  // ADD THIS
            "/applicant/documents/",  // ADD THIS

            "/admins/create",
            "/admins/login",
            "/admins/verify-email-password-reset",      // ✅
            "/admins/reset-password",                   // ✅
            "/admins/validate-token",
            "/admins/test-token",
            "/auth/",
            "/capstone/applicants/create",
            "/capstone/applicants/login",
            "/capstone/applicants/verify-email-password-reset",  // ✅
            "/capstone/applicants/reset-password",               // ✅
            "/capstone/applicants/validate-token",
            "/capstone/applicants/test-token",
            "/capstone/applicants/test-public",
            // ✅ ADD DOCUMENT ENDPOINTS HERE
            "/capstone/applicant/documents/",  // ADD THIS
            "/capstone/applicant/documents/",  // ADD THIS
            "/capstone/admins/create",
            "/capstone/admins/login",
            "/capstone/admins/verify-email-password-reset",      // ✅
            "/capstone/admins/reset-password",                   // ✅
            "/capstone/admins/validate-token",
            "/capstone/admins/test-token"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        System.out.println("🔍 JWT Filter processing request: " + requestURI);

        // ✅ SKIP AUTHENTICATION FOR PUBLIC ENDPOINTS
        if (isPublicEndpoint(requestURI)) {
            System.out.println("🔓 JWT Filter: Allowing public endpoint without authentication: " + requestURI);
            chain.doFilter(request, response);
            return;
        }

        final String requestTokenHeader = request.getHeader("Authorization");

        String username = null;
        String jwtToken = null;

        // JWT Token is in the form "Bearer token"
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                username = tokenUtil.extractUsername(jwtToken);
                System.out.println("✅ Extracted username from token: " + username);
            } catch (Exception e) {
                System.out.println("❌ Unable to get JWT Token: " + e.getMessage());
            }
        } else {
            System.out.println("ℹ️ No Bearer token found or invalid format for: " + requestURI);

            // If it's a protected endpoint without token, let Spring Security handle it
            // Don't block the request here - let the security configuration handle authorization
            chain.doFilter(request, response);
            return;
        }

        // Validate token and set authentication
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                System.out.println("✅ Loaded user details for: " + username);

                if (tokenUtil.validateToken(jwtToken)) {
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    System.out.println("✅ User authenticated: " + username + " with roles: " + userDetails.getAuthorities());
                } else {
                    System.out.println("❌ Token validation failed for user: " + username);
                }
            } catch (Exception e) {
                System.out.println("❌ Error loading user details: " + e.getMessage());
            }
        }
        chain.doFilter(request, response);
    }

    // Check if the request URI matches any public endpoint
    private boolean isPublicEndpoint(String requestURI) {
        return PUBLIC_ENDPOINTS.stream().anyMatch(requestURI::contains);
    }

    // Alternative: More specific path matching
    private boolean isPublicEndpointV2(String requestURI) {
        // Remove context path if any
        String path = requestURI.replace("/capstone", "");

        return PUBLIC_ENDPOINTS.stream().anyMatch(endpoint ->
                path.equals(endpoint) || path.startsWith(endpoint + "/")
        );
    }
}