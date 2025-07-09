package com.springboot.hospital_backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.springboot.hospital_backend.security.JwtFilter;

@Configuration
@EnableWebSecurity

public class SecurityConfig {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(request -> request
                .requestMatchers("/auth/authenticate", "/auth/register")
                .permitAll()
                .requestMatchers("/doctors", "/doctors/find/**","/medicalServices",
                    "/packages", "/appointments", "/appointments/find/**").hasAnyRole("ADMIN", "PATIENT", "DOCTOR")

                .requestMatchers("/patients", "/patients/find/**", "/schedules/find/allTimes/**").hasAnyRole("ADMIN","DOCTOR")

                .requestMatchers("/medicalServices/find/**", "/packages/find/**", "/schedules/find/availavility/**", 
                    "/schedules/find/times/**").hasAnyRole("DOCTOR", "PATIENT")

                .requestMatchers("/appointments/save").hasAnyRole("DOCTOR", "PATIENT")
                .requestMatchers("/appointments/doctor/**").hasAnyRole("DOCTOR")

                .requestMatchers("/users/patientDTO/**", "/doctors/speciality/**", "/doctors/specialities",
                    "/appointments/**").hasRole("PATIENT")
                    
                .requestMatchers("/appointments/**", "/doctors/**", "/medicalServices/**", "/packages/**",
                    "/patients/**", "/schedules/**", "/specialities/**").hasRole("ADMIN")
                .anyRequest()
                .authenticated()
            )
            .httpBasic(Customizer.withDefaults())
            .addFilterBefore(this.jwtFilter, UsernamePasswordAuthenticationFilter.class)
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(this.userDetailsService);

        return provider;    
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration http) throws Exception {
        return http.getAuthenticationManager();
    }
}
