package com.interviewmate.apigateway.security;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationFilter implements GlobalFilter {

    private final RouteValidator validator;
    private final JwtUtil jwtUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        // Check if the route is secured
        if (validator.isSecured.test(request)) {

            // Validate Authorization header
            String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                log.warn("Missing or invalid Authorization header for request: {}", request.getURI());
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            String token = authHeader.substring(7);

            try {
                // Validate JWT token
                if (!jwtUtil.validateToken(token)) {
                    log.warn("Invalid or expired token for request: {}", request.getURI());
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return exchange.getResponse().setComplete();
                }

                // Extract claims
                String username = jwtUtil.extractUsername(token);
                UUID userId = jwtUtil.extractUserId(token);
                String role = jwtUtil.extractRole(token);

                // Add custom headers for downstream services
                request = request.mutate()
                        .header("x-username", username)
                        .header("x-user-id", userId.toString())
                        .header("x-role", role)
                        .build();

            } catch (Exception e) {
                log.error("Unauthorized access attempt: {}", e.getMessage());
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
        }

        // Continue filter chain with possibly mutated request
        return chain.filter(exchange.mutate().request(request).build());
    }
}