package com.evento.Cadastro.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfiguration {

    // Configuração de CORS
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:4200", "https://event-fy.vercel.app"));  // Domínios permitidos
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));  // Métodos permitidos
        config.setAllowCredentials(true);  // Permitir cookies/tokens
        config.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept", "X-Requested-With"));  // Cabeçalhos permitidos

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);  // Aplica para todas as rotas
        return source;
    }

    // Configuração de segurança usando SecurityFilterChain
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // Desabilita CSRF
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))  // Aplica CORS
                .authorizeRequests()
                // Permitir acesso público para a rota POST /eventos/criarEvento
                .requestMatchers("/eventos/criarEvento").permitAll()
                // Requer autenticação para todas as outras requisições
                .anyRequest().authenticated()
                .and()
                .formLogin().disable();  // Desabilitar formulário de login (se não for usar autenticação baseada em formulário)

        return http.build();
    }
}
