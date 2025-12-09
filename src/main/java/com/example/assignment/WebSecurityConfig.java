package com.example.assignment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(securedEnabled = true, proxyTargetClass = true)

public class WebSecurityConfig {
    @Autowired
    private UserDetailsService userDetailsService;
    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //http.csrf(csrf -> csrf.disable())
        http
                .authorizeHttpRequests(auth -> auth
                        // Public (no login required)
                        .requestMatchers("/", "/home", "/register", "/register_process",
                                "/contact", "/passwordtest", "/messages/**").permitAll()
                        .requestMatchers("/resources/**").permitAll()   // static files (best practice)

                        // Admin only
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        // Logged-in users
                        .requestMatchers("/crud/**").authenticated()

                        // Everything else requires authentication
                        .anyRequest().authenticated()
                )
                .formLogin(
                        form -> form
                                .defaultSuccessUrl("/home").permitAll()
                ).logout(
                        logout -> logout
                                .logoutSuccessUrl("/")
                                .permitAll()
                );
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

}
