package com.example.bank.security;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    @ConditionalOnProperty(prefix = "app.security", name = "type", havingValue = "inMemory")
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @ConditionalOnProperty(prefix = "app.security", name = "type", havingValue = "inMemory")
    public UserDetailsService userDetailsService() {

        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();

        manager.createUser(User.withUsername("user")
                .password(passwordEncoder().encode("user"))
                .roles("USER")
                .build());

        manager.createUser(User.withUsername("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN")
                .build());

        return manager;
    }

    @Bean
    @ConditionalOnProperty(prefix = "app.security", name = "type", havingValue = "inMemory")
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity,
                                                       UserDetailsService userDetailsService) throws Exception {
        var authManagerBuilder = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
        authManagerBuilder.userDetailsService(userDetailsService);
        return authManagerBuilder.build();
    }

    @Bean
    @ConditionalOnProperty(prefix = "app.security", name = "type", havingValue = "inMemory")
    SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager manager) throws Exception {

        http
                .authorizeHttpRequests((auth) ->
                    auth.requestMatchers("/auth/**").hasAnyRole("USER", "ADMIN")
                        .anyRequest().authenticated())
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults())
                .authenticationManager(manager);

        return http.build();
    }
}

