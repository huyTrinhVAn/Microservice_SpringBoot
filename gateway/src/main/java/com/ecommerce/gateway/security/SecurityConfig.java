package com.ecommerce.gateway.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    private static final String CLIENT_ID = "oauth2-pkce";

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchange -> exchange
//                        .pathMatchers("/api/products/**").hasRole("PRODUCT")
//                        .pathMatchers("/api/orders/**").hasRole("ORDER")
//                        .pathMatchers("/api/users/**").hasRole("USER")
                        .anyExchange().authenticated())
                .oauth2ResourceServer(oauth2 ->
                        oauth2.jwt(jwt ->
                                jwt.jwtAuthenticationConverter(grantedAuthoritiesExtractor())))
                .build();
    }

    private Converter<Jwt, Mono<AbstractAuthenticationToken>> grantedAuthoritiesExtractor() {
        ReactiveJwtAuthenticationConverter jwtAuthenticationConverter =
                new ReactiveJwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwt -> {
            Object resourceAccessClaim = jwt.getClaims().get("resource_access");

            List<String> roles = extractClientRoles(resourceAccessClaim)
                    .toList();

            System.out.println("Extracted Roles: " + roles);

            return Flux.fromIterable(roles)
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role));

        });
        return jwtAuthenticationConverter;
    }

    private Stream<String> extractClientRoles(Object resourceAccessClaim) {
        if (!(resourceAccessClaim instanceof Map<?, ?> resourceAccess)) {
            return Stream.empty();
        }

        Object clientAccess = resourceAccess.get(CLIENT_ID);
        if (!(clientAccess instanceof Map<?, ?> clientAccessMap)) {
            return Stream.empty();
        }

        Object roles = clientAccessMap.get("roles");
        if (!(roles instanceof Collection<?> roleCollection)) {
            return Stream.empty();
        }

        return roleCollection.stream()
                .filter(Objects::nonNull)
                .map(String::valueOf)
                .filter(role -> !role.isBlank());
    }
}
