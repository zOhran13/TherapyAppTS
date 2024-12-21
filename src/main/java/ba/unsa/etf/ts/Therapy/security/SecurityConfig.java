package ba.unsa.etf.ts.Therapy.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtTokenFilter jwtTokenFilter;

    public SecurityConfig(JwtTokenFilter jwtTokenFilter) {
        this.jwtTokenFilter = jwtTokenFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.disable())
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Swagger/OpenAPI endpoints
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**",
                                "/v3/api-docs.yaml",
                                "/swagger-resources/**",
                                "/webjars/**"
                        ).permitAll()

                        // Public routes (registration, login)
                        .requestMatchers("/api/users/registerPsychologist", "/api/users/registerPatient", "/api/users/login")
                        .permitAll()

                        // Patient routes
                        .requestMatchers("/api/daily-reports/**", "/stressrelief/**", "/api/meditation-tools/**")
                        .hasAuthority("ROLE_PATIENT")

                        // Psychotherapist routes
                        .requestMatchers("/api/articles/**", "/api/patients/**", "/api/weekly-reports/**")
                        .hasAuthority("ROLE_PSYCHOLOGIST")

                        // Administrator routes
                        .requestMatchers("/api/users/**").hasAuthority("ROLE_ADMINISTRATOR")

                        // Generic rule for other routes
                        .requestMatchers("/api/**").permitAll()

                        // Default rule for all other routes
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
