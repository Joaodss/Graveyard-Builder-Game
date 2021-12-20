package com.ironhack.gatewayservice.filter;

import com.ironhack.gatewayservice.util.JwtUtil;
import com.ironhack.gatewayservice.util.RouterValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON;

/**
 * Authentication Filter.
 * Filters to validate the jwt token.
 * Fails if the token is invalid or authorization is not sufficient.
 * Accepts paths that are not protected.
 * Based on: https://oril.co/blog/spring-cloud-gateway-security-with-jwt/
 */
@RefreshScope
@Component
@RequiredArgsConstructor
@Slf4j
public class AuthenticationFilter implements GatewayFilter {
    private final JwtUtil jwtUtil;
    private final RouterValidator routerValidator;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        var request = exchange.getRequest();
        if (routerValidator.isSecured(request)) {
            if (isAuthMissing(request))
                return this.onError(exchange, "Authorization header is missing in request", UNAUTHORIZED);
            final String token = getAuthHeader(request);
            try {
                if (jwtUtil.isInvalid(token))
                    return this.onError(exchange, "Authorization header is invalid", UNAUTHORIZED);
                if (isAuthInvalid(request))
                    return this.onError(exchange, "User is not authorized to access this resource", FORBIDDEN);
                populateRequestWithHeaders(exchange, token);
            } catch (Exception e) {
                log.info("Invalid token. Error: {}", e.getMessage());
                return this.onError(exchange, "Invalid token", UNAUTHORIZED);
            }
        }
        return chain.filter(exchange);
    }


    // -------------------- Private aux methods --------------------
    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        response.getHeaders().setContentType(APPLICATION_JSON);

        byte[] bytes = err.getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bytes);
        return response.writeWith(Flux.just(buffer));
    }

    private String getAuthHeader(ServerHttpRequest request) {
        return request.getHeaders().getOrEmpty("Authorization").get(0);
    }

    private boolean isAuthMissing(ServerHttpRequest request) {
        return !request.getHeaders().containsKey("Authorization") || getAuthHeader(request).isEmpty();
    }

    private boolean isAuthInvalid(ServerHttpRequest request) {
        log.info("Validating token roles...");
        var authHeader = getAuthHeader(request);
        var roles = List.of(jwtUtil.getRolesFromToken(authHeader));
        if (routerValidator.isUserRestricted(request)) {
            return !(roles.contains("ROLE_USER") || roles.contains("ROLE_ADMIN"));
        }
        if (routerValidator.isAdminRestricted(request)) {
            return !roles.contains("ROLE_ADMIN");
        }
        return false;
    }

    private void populateRequestWithHeaders(ServerWebExchange exchange, String authHeader) {
        var username = jwtUtil.getUsernameFromToken(authHeader);
        exchange.getRequest().mutate()
                .header("username", username)
                .build();
    }

}
