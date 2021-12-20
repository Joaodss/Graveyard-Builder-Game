package com.ironhack.gatewayservice.util;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Router Validator.
 * To verify the required access level of the route.
 * Based on: https://oril.co/blog/spring-cloud-gateway-security-with-jwt/
 */
@Component
public class RouterValidator {

    // List of public available endpoints
    private final List<String> publicEndpoints = List.of(
            "/api/v1/signIn",
            "/login"
    );

    // List of user restricted endpoints
    private final List<String> userRestrictedEndpoints = List.of(
            "/api/v1/profiles",
            "/api/v1/party-manager",
            "/api/v1/battle"
    );

    // List of admin restricted endpoints
    private final List<String> adminRestrictedEndpoints = List.of(
            "/api/v1/users",
            "/api/v1/characters",
            "/api/v1/opponent"
    );


    // -------------------- Methods to verify the access level of the request --------------------
    public boolean isSecured(ServerHttpRequest request) {
        return publicEndpoints.stream()
                .noneMatch(uri -> request.getURI().getPath().contains(uri));
    }

    public boolean isUserRestricted(ServerHttpRequest request) {
        return userRestrictedEndpoints.stream()
                .anyMatch(uri -> request.getURI().getPath().contains(uri));
    }

    public boolean isAdminRestricted(ServerHttpRequest request) {
        return adminRestrictedEndpoints.stream()
                .anyMatch(uri -> request.getURI().getPath().contains(uri));
    }

}
