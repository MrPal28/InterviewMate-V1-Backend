package com.interviewmate.apigateway.security;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Slf4j
@Component
@Getter
public class RouteValidator {

    // List of unsecured paths (whitelisted)
    private final List<String> openApiEndpoints = List.of(
            "/api/v1/auth/login",
            "/api/v1/auth/register",
            "/api/v1/auth/getotp",
            "/api/v1/auth/verifyotp",
            "/api/v1/auth/forgot-password",
            "/api/v1/auth/reset-password",
            "/eureka"
    );

    /**
     * Predicate to check if a request is secured.
     * Returns true if request path is NOT in the whitelist (secured route).
     */
    public final Predicate<ServerHttpRequest> isSecured = request -> {
        String path = request.getURI().getPath();
        boolean secured = openApiEndpoints.stream().noneMatch(path::startsWith);
        log.debug("Request path: {} | Secured: {}", path, secured);
        return secured;
    };
}