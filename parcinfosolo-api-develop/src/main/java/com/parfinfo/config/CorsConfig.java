package com.parfinfo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        
        // Autoriser les cookies
        config.setAllowCredentials(true);
        
        // Autoriser les origines spécifiques
        config.addAllowedOrigin("http://localhost:3000"); // Frontend React
        config.addAllowedOrigin("https://parcinfosolo.com"); // Production
        
        // Autoriser les en-têtes
        config.addAllowedHeader("*");
        
        // Autoriser les méthodes HTTP
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("OPTIONS");
        
        // Exposer les en-têtes
        config.addExposedHeader("Authorization");
        
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
} 