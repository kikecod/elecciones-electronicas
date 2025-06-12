package com.eleccioneselectronicas.config;

// En un paquete 'security' o 'config'
import com.eleccioneselectronicas.security.HeadersAuthenticationFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.context.annotation.Bean;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // ¡Esto activa las anotaciones @PreAuthorize!
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        boolean modoDesarrollo = true; // Cambia según tu entorno
        if (modoDesarrollo) {
            http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
        } else {
            http.csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                    .anyRequest().hasAnyAuthority("ROLE_ADMIN")
                )
                .addFilterBefore(new HeadersAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        }
        return http.build();
    }
}
