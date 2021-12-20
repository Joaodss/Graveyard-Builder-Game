package com.ironhack.gatewayservice.configuration;

import com.ironhack.gatewayservice.filter.AuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Gateway Configuration.
 * Routes to the microservices.
 * Implements authentication filter for all routes.
 */
@Configuration
@RequiredArgsConstructor
public class GatewayConfiguration {
    private final AuthenticationFilter filter;

    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
        return builder.routes()

                // Public routes
                .route("auth-service", r -> r.path("/login/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://auth-service"))

                .route("signin-service", r -> r.path("/api/v1/signIn/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://signin-service"))

                // User/Admin routes
                .route("profile-service", r -> r.path("/api/v1/profiles/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://profile-service"))

                .route("party-service", r -> r.path("/api/v1/party-manager/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://party-service"))

                .route("battle-service", r -> r.path("/api/v1/battle/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://battle-service"))

                // Admin routes
                .route("opponent-selection-service", r -> r.path("/api/v1/opponent/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://opponent-selection-service"))

                .route("user-model-service", r -> r.path("/api/v1/users/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://user-model-service"))

                .route("character-model-service", r -> r.path("/api/v1/characters/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://character-model-service"))

                .build();
    }

}
