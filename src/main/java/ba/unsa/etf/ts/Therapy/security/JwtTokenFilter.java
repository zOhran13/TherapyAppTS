package ba.unsa.etf.ts.Therapy.security;

import ba.unsa.etf.ts.Therapy.models.UserEntity;
import ba.unsa.etf.ts.Therapy.repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenHelper jwtTokenHelper;
    private final UserRepository userRepository;

    public JwtTokenFilter(JwtTokenHelper jwtTokenHelper, UserRepository userRepository) {
        this.jwtTokenHelper = jwtTokenHelper;
        this.userRepository = userRepository;
    }
    /*@Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        // Skip filter for all paths (test mode)
        chain.doFilter(request, response);
    }*/


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        // Skip filter for Swagger endpoints
        String path = request.getRequestURI();
        if (isSwaggerPath(path)) {
            chain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                if (jwtTokenHelper.validateTokenAndItsClaims(token, List.of("Administrator", "Psychologist", "Patient"))) {
                    String username = jwtTokenHelper.getUsernameFromToken(token);

                    UserEntity userDetails = userRepository.findByEmail(username)
                            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

                    var authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());

                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (Exception e) {
                // Log the error but don't throw it
                logger.error("Could not set user authentication in security context", e);
            }
        }
        chain.doFilter(request, response);
    }

    private boolean isSwaggerPath(String path) {
        return path.contains("/swagger-ui") ||
                path.contains("/v3/api-docs") ||
                path.contains("/swagger-resources") ||
                path.contains("/webjars");
    }
}