package com.ecommerce.gateway;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()

                // Product Service
                .route("product-service", r -> r
                        .path("/api/products", "/api/products/**")
                        .filters(f -> f.circuitBreaker(config -> config
                                .setName("ecomBreaker")
                                .setFallbackUri("forward:/fallback/products")))
                        .uri("lb://PRODUCT-SERVICE"))

                // User Service
                .route("user-service", r -> r
                        .path("/api/users", "/api/users/**")
                        .uri("lb://USER-SERVICE"))

                // Order Service
                .route("order-service", r -> r
                        .path("/api/orders", "/api/orders/**", "/api/cart", "/api/cart/**")
                        .uri("lb://ORDER-SERVICE"))

                // Eureka Dashboard
                .route("eureka-server", r -> r
                        .path("/eureka/main")
                        .filters(f -> f.setPath("/"))
                        .uri("http://localhost:8761"))

                // Eureka Static Resources
                .route("eureka-server-static", r -> r
                        .path("/eureka/**")
                        .uri("http://localhost:8761"))

                .build();
    }
}