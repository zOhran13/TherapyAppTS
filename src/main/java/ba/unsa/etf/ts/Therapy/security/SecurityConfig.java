package ba.unsa.etf.ts.Therapy.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Šifriranje lozinki
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Isključi CSRF zaštitu
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // Dozvoli sve zahtjeve
                )
                .formLogin(form -> form.disable()) // Isključi form login
                .httpBasic(basic -> basic.disable()); // Isključi HTTP Basic autentifikaciju

        return http.build();
    }
}
