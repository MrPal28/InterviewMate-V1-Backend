package com.interviewmate.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@Configuration
public class CorsConfig {

    @Value("${frontend.allowed-origins}")
    private List<String> allowedOrigins;

    @Bean
    CorsWebFilter corsWebFilter() {
        CorsConfiguration corsConfig = new CorsConfiguration();

        // Only allow your trusted frontend
        corsConfig.setAllowedOrigins(allowedOrigins);

        // Specify allowed headers explicitly
        corsConfig.setAllowedHeaders(List.of(
                "Authorization",
                "Content-Type",
                "Accept",
                "Origin",
                "X-Requested-With"
        ));

        // Specify allowed HTTP methods
        corsConfig.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // Only allow credentials if necessary (cookies, auth headers)
        corsConfig.setAllowCredentials(true);

        // Set how long pre-flight request is cached (seconds)
        corsConfig.setMaxAge(3600L);

        // Register the configuration for all paths
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);

        // Return a reactive CORS filter
        return new CorsWebFilter(source);
    }
}
