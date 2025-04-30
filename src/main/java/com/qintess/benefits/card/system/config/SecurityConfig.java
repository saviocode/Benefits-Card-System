package com.qintess.benefits.card.system.config;

import com.qintess.benefits.card.system.config.filter.JwtAuthenticationFilter;
import com.qintess.benefits.card.system.service.impl.UserDetailsServiceImpl;
import jakarta.servlet.Filter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserDetailsServiceImpl userDetailsService;
    private final RequestMatcher permitAllRequestMatcher;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter,
                          UserDetailsServiceImpl userDetailsService, RequestMatcher permitAllRequestMatcher) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.userDetailsService = userDetailsService;
        this.permitAllRequestMatcher = permitAllRequestMatcher;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Define o gerenciamento de sessão como STATELESS, pois o JWT é stateless
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Desabilita o CSRF, já que usaremos tokens (JWT)
                .csrf(AbstractHttpConfigurer::disable)
                // Configura as requisições autorizadas
                .authorizeHttpRequests(authorize -> authorize
                        // Permite endpoints de autenticação (por exemplo, /api/auth/**)
                        .requestMatchers(permitAllRequestMatcher).permitAll()

                        // Qualquer outra requisição precisa ser autenticada
                        .anyRequest().authenticated()
                )
                // Configura o serviço que carrega os detalhes do usuário
                .userDetailsService(userDetailsService);

        // Adiciona o filtro de autenticação JWT antes do filtro padrão de autenticação
        http.addFilterBefore((Filter) jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    // Definição do PasswordEncoder (usando BCrypt)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
