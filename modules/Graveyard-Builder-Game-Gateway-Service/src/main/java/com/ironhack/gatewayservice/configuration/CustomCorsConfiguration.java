package com.ironhack.gatewayservice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

import static org.springframework.http.HttpHeaders.SET_COOKIE;

/**
 * CORS Configuration.
 * Based on: https://stackoverflow.com/questions/61909640/how-to-disable-cors-in-spring-cloud-gateway
 */
@Configuration
public class CustomCorsConfiguration implements WebFluxConfigurer {

    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addAllowedOrigin("http://109.237.25.160");
        corsConfiguration.addAllowedOrigin("http://graveyardbuildergame.site");
        corsConfiguration.addAllowedOrigin("http://localhost:4200");
        corsConfiguration.addExposedHeader(SET_COOKIE);
        UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();
        corsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsWebFilter(corsConfigurationSource);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedOrigins("http://109.237.25.160", "http://graveyardbuildergame.site", "http://localhost:4200")
                .allowedHeaders("*")
                .allowedMethods("*")
                .exposedHeaders(SET_COOKIE);
    }

}
