package com.ironhack.gatewayservice.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.sql.Date;

import static java.time.Instant.now;

/**
 * JWT Utils.
 * Util class to validate and extract information from JWT token.
 * Based on: https://oril.co/blog/spring-cloud-gateway-security-with-jwt/
 * Adapted with: com.auth0.java-jwt dependency.
 */
@Component
@Slf4j
public class JwtUtil {
    public static final String SECRET = "secretPass";


    // -------------------- Main util methods --------------------
    public boolean isInvalid(String authHeader) {
        log.info("Validating token");
        return authHeader.length() <= "Bearer ".length() || isTokenExpired(authHeader);
    }

    public String getUsernameFromToken(String authHeader) {
        log.info("Getting username from token");
        return getDecodedToken(authHeader)
                .getSubject();
    }

    public String[] getRolesFromToken(String authHeader) {
        log.info("Getting roles from token");
        return getDecodedToken(authHeader)
                .getClaim("roles")
                .asArray(String.class);
    }


    // -------------------- Private aux methods --------------------
    private boolean isTokenExpired(String authHeader) {
        log.info("Checking if token is expired");
        return getDecodedToken(authHeader)
                .getExpiresAt()
                .before(Date.from(now()));
    }

    private DecodedJWT getDecodedToken(String authHeader) {
        var token = authHeader.substring("Bearer ".length());
        Algorithm algorithm = Algorithm.HMAC256(SECRET.getBytes());

        JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier.verify(token);
    }

}
