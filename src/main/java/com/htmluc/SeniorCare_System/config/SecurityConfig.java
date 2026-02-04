package com.htmluc.SeniorCare_System.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
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
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authz -> authz
                
                .requestMatchers(
                    "/",        
                    "/login",               
                    "/css/**",              
                    "/js/**",               
                    "/images/**",          
                    "/error"        
                ).permitAll()
                
                .requestMatchers(
                    "/v1/users/**",
                    "/h2-console/**"       
                ).hasRole("ADMIN")
                
                .requestMatchers(
                    "/v1/patients/**",
                    "/v1/familyContact/**"
                ).hasAnyRole("ADMIN", "ENFERMEIRO")
                
                .anyRequest().authenticated()
            )
            
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/dashboard", true)
                .failureUrl("/login?error=true")
                .permitAll()
            )
            
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .permitAll()
            )
            
            .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))
            .csrf(csrf -> csrf.disable());
        
        return http.build();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.builder()
            .username("João Augusto")
            .password(passwordEncoder().encode("admin123"))
            .roles("ADMIN")  
            .build();
        
        UserDetails enfermeiro = User.builder()
            .username("Luciano Costa")
            .password(passwordEncoder().encode("senha123"))
            .roles("ENFERMEIRO")  
            .build();
            
        return new InMemoryUserDetailsManager(admin, enfermeiro);
    }
}