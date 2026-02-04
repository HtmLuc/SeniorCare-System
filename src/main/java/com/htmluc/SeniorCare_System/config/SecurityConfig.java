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
                
                // ROTAS PÚBLICAS
                .requestMatchers(
                    "/",        
                    "/login",               
                    "/css/**",              
                    "/js/**",               
                    "/images/**",          
                    "/swagger-ui/**",       
                    "/v3/api-docs/**",      
                    "/public/**"        
                ).permitAll()
                
                // ROTAS DO ADMIN (SOMENTE ADMIN)
                .requestMatchers(
                    "/v1/users/**",
                    "/h2-console/**"       
                ).hasRole("ADMIN")
                
                
                // ROTAS DO USER E ADMIN
                .requestMatchers(
                    "/v1/patients/**",
                    "/v1/familyContact/**"
                ).hasAnyRole("ADMIN", "ENFERMEIRO")
                
                // DASHBOARD (qualquer usuário logado pode acessar)
                .requestMatchers("/dashboard").authenticated()
                
                // QUALQUER OUTRA ROTA precisa de autenticação
                .anyRequest().authenticated()
            )
            
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/dashboard", true)
                .failureUrl("/login?error=true")
                .permitAll()
            )
            
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            )
            
            .headers(headers -> headers
                .frameOptions(frame -> frame.sameOrigin())
            )
            .csrf(csrf -> csrf.disable())
            .httpBasic(httpBasic -> {});
        
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