package ba.unsa.etf.ts.Therapy.security;

import ba.unsa.etf.ts.Therapy.dto.RoleDto;
import ba.unsa.etf.ts.Therapy.dto.UserDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Calendar;

@Component
public class JwtTokenHelper {

    @Value("${jwt.secret:defaultSecretKey}")
    private String secretKey;

    private SecretKey key;

    public void init() {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(UserDto user, RoleDto role) {
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.DAY_OF_MONTH, 2);

        if (key == null) {
            init();
        }

        return Jwts.builder()
                .setSubject(user.getUserId())
                .setIssuedAt(now)
                .setExpiration(calendar.getTime())
                .setIssuer("TherapyApp")
                .claim("name", user.getName())
                .claim("email", user.getEmail())
                .claim("role", role.getName())
                .signWith(key)
                .compact();
    }

    public boolean validateTokenAndItsClaims(String token, List<String> allowedRoles) {
        try {
            if (key == null) {
                init();
            }

            Claims claims = Jwts.parser()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            if (claims.getExpiration().before(new Date())) {
                return false;
            }

            String userRole = claims.get("role", String.class);
            return allowedRoles.isEmpty() || allowedRoles.contains(userRole);
        } catch (Exception ex) {
            return false;
        }
    }

    public String getUsernameFromToken(String token) {
        if (key == null) {
            init();
        }

        return getClaimsFromToken(token).getSubject();
    }

    public Claims getClaimsFromToken(String token) {
        if (key == null) {
            init();
        }

        return Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
