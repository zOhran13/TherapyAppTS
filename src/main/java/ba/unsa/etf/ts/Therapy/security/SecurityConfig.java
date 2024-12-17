package ba.unsa.etf.ts.Therapy.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
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
                .authorizeHttpRequests(auth -> auth
                        // Javne rute
                        .requestMatchers("/api/auth/**").permitAll()

                        // Swagger rute su javne
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-ui.html"
                        ).permitAll()

                        // Prava za pacijente
                        .requestMatchers("/stressrelief/**", "/api/meditation-tools/**", "/api/daily-reports/**").hasRole("PATIENT")

                        // Prava za psihoterapeute
                        .requestMatchers("/api/articles/**", "/api/patients/**", "/api/weekly-reports/**").hasRole("PSYCHOLOGIST")

                        // Prava za administratore
                        .requestMatchers("/api/users/**", "/api/**").hasRole("ADMINISTRATOR")

                        // Svi ostali zahtjevi su zabranjeni
                        .anyRequest().denyAll()
                )
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
