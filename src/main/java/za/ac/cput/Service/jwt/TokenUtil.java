package za.ac.cput.Service.jwt;

import org.springframework.stereotype.Component;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Component
public class TokenUtil {

    private final long expiration = 86400000; // 24 hours

    public String generateToken(String email, String role, Integer userId, String userType) {
        try {
            // Create a simple token structure
            String tokenData = String.format(
                    "email:%s|role:%s|userId:%d|userType:%s|issued:%d|expires:%d",
                    email, role, userId, userType,
                    System.currentTimeMillis(),
                    System.currentTimeMillis() + expiration
            );

            // Encode to base64
            String token = Base64.getEncoder().encodeToString(tokenData.getBytes());
            System.out.println("🔐 TOKEN GENERATED: " + token);
            System.out.println("🔐 TOKEN DATA: " + tokenData);

            return token;

        } catch (Exception e) {
            System.out.println("❌ Error generating token: " + e.getMessage());
            throw new RuntimeException("Token generation failed", e);
        }
    }

    public boolean validateToken(String token) {
        try {
            System.out.println("🔐 VALIDATING TOKEN: " + token);
            Map<String, String> tokenData = parseToken(token);
            if (tokenData == null) {
                System.out.println("❌ TOKEN VALIDATION: Failed to parse token");
                return false;
            }

            // Check expiration
            long expires = Long.parseLong(tokenData.get("expires"));
            boolean isValid = System.currentTimeMillis() < expires;

            System.out.println("🔐 TOKEN VALIDATION RESULT: " + isValid);
            System.out.println("🔐 TOKEN EXPIRES: " + expires + " | CURRENT: " + System.currentTimeMillis());

            return isValid;

        } catch (Exception e) {
            System.out.println("❌ TOKEN VALIDATION ERROR: " + e.getMessage());
            return false;
        }
    }

    public String extractUsername(String token) {
        try {
            Map<String, String> tokenData = parseToken(token);
            String username = tokenData != null ? tokenData.get("email") : null;
            System.out.println("🔐 EXTRACTED USERNAME: " + username);
            return username;
        } catch (Exception e) {
            System.out.println("❌ ERROR EXTRACTING USERNAME: " + e.getMessage());
            return null;
        }
    }

    public Integer extractUserId(String token) {
        try {
            Map<String, String> tokenData = parseToken(token);
            Integer userId = tokenData != null ? Integer.parseInt(tokenData.get("userId")) : 0;
            System.out.println("🔐 EXTRACTED USER ID: " + userId);
            return userId;
        } catch (Exception e) {
            System.out.println("❌ ERROR EXTRACTING USER ID: " + e.getMessage());
            return 0;
        }
    }

    public String extractUserType(String token) {
        try {
            Map<String, String> tokenData = parseToken(token);
            String userType = tokenData != null ? tokenData.get("userType") : null;
            System.out.println("🔐 EXTRACTED USER TYPE: " + userType);
            return userType;
        } catch (Exception e) {
            System.out.println("❌ ERROR EXTRACTING USER TYPE: " + e.getMessage());
            return null;
        }
    }

    public String extractRole(String token) {
        try {
            Map<String, String> tokenData = parseToken(token);
            String role = tokenData != null ? tokenData.get("role") : null;
            System.out.println("🔐 EXTRACTED ROLE: " + role);

            // ✅ CRITICAL: Ensure role has ROLE_ prefix for Spring Security
            if (role != null && !role.startsWith("ROLE_")) {
                role = "ROLE_" + role;
                System.out.println("🔐 FIXED ROLE: " + role);
            }

            return role;
        } catch (Exception e) {
            System.out.println("❌ ERROR EXTRACTING ROLE: " + e.getMessage());
            return null;
        }
    }

    private Map<String, String> parseToken(String token) {
        try {
            String decoded = new String(Base64.getDecoder().decode(token));
            System.out.println("🔐 DECODED TOKEN: " + decoded);

            Map<String, String> data = new HashMap<>();

            String[] pairs = decoded.split("\\|");
            for (String pair : pairs) {
                String[] keyValue = pair.split(":", 2);
                if (keyValue.length == 2) {
                    data.put(keyValue[0], keyValue[1]);
                    System.out.println("🔐 TOKEN FIELD: " + keyValue[0] + " = " + keyValue[1]);
                }
            }

            System.out.println("🔐 PARSED TOKEN DATA: " + data);
            return data;

        } catch (Exception e) {
            System.out.println("❌ ERROR PARSING TOKEN: " + e.getMessage());
            return null;
        }
    }
}