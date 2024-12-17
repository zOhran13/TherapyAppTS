package ba.unsa.etf.ts.Therapy.security;

import ba.unsa.etf.ts.Therapy.dto.RoleDto;
import ba.unsa.etf.ts.Therapy.dto.UserDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Calendar;

@Component
public class JwtTokenHelper {

    /**
     * Generiše JWT token na osnovu korisničke role.
     */
    public String generateToken(UserDto user, RoleDto role) {
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.DAY_OF_MONTH, 2);  // Token vrijedi 2 dana

        // Generišemo dinamički ključ na osnovu imena role
        SecretKey key = getKeyFromRole(role.getName());

        return Jwts.builder()
                .setSubject(user.getUserId())
                .setIssuedAt(now)
                .setExpiration(calendar.getTime())
                .setIssuer("TherapyApp")
                .claim("name", user.getName())
                .claim("email", user.getEmail())
                .claim("role", role.getName())  // Dodajemo rolu korisnika
                .signWith(key)
                .compact();
    }

    /**
     * Validira JWT token i provjerava rolu.
     */
    public boolean validateTokenAndItsClaims(String token, List<String> allowedRoles) {
        try {
            Claims claims = getClaimsFromToken(token);

            if (claims.getExpiration().before(new Date())) {
                return false;  // Token je istekao
            }

            String userRole = claims.get("role", String.class);
            return allowedRoles.isEmpty() || allowedRoles.contains(userRole);  // Provjera role
        } catch (Exception ex) {
            return false;  // Token nije validan
        }
    }

    /**
     * Vraća korisničko ime iz tokena.
     */
    public String getUsernameFromToken(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    /**
     * Parsira JWT token i vraća tvrdnje (claims).
     */
    public Claims getClaimsFromToken(String token) {
        String role = Jwts.parser()
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("role", String.class);

        SecretKey key = getKeyFromRole(role);  // Generišemo ključ na osnovu role
        return Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Generiše ključ za JWT potpisivanje na osnovu korisničke role.
     */
    private SecretKey getKeyFromRole(String roleName) {
        String dynamicKey = roleName + "_SecretKey";
        return Keys.hmacShaKeyFor(dynamicKey.getBytes(StandardCharsets.UTF_8));
    }
}
