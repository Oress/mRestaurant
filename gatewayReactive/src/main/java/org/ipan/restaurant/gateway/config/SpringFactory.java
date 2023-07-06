package org.ipan.restaurant.gateway.config;

import org.ipan.restaurant.gateway.AppUriBuilder;
import org.ipan.restaurant.gateway.filters.JwtAuthHeaderGlobalFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.RewriteLocationResponseHeaderGatewayFilterFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.MalformedURLException;
import java.text.ParseException;

@Configuration
public class SpringFactory {
    @Bean
    public RouteLocator configureRoute(RouteLocatorBuilder builder) {
        RouteLocatorBuilder.Builder routes = builder.routes();
        return addAuthRoute(routes)
                .route("inventory", r -> r.path("/inventory/**").uri("lb://inventory-service"))
                .build();
    }

    private RouteLocatorBuilder.Builder addAuthRoute(RouteLocatorBuilder.Builder routes) {
        RewriteLocationResponseHeaderGatewayFilterFactory.Config config = new RewriteLocationResponseHeaderGatewayFilterFactory.Config();
        config.setHostValue("localhost:8001");
        config.setProtocols("http");
        GatewayFilter filter = new RewriteLocationResponseHeaderGatewayFilterFactory().apply(config);
        return routes.route("auth", r -> r.path("/auth/**").filters((spec) -> spec.filter(filter)).uri("lb://auth-service"));
    }

    @Bean
    public JwtAuthHeaderGlobalFilter jwtAuthHeaderGlobalFilter() throws ParseException, MalformedURLException {
        return new JwtAuthHeaderGlobalFilter(AppUriBuilder.publicKeyUrl(), AppUriBuilder.loginPageUri());
    }

    @Bean
    @LoadBalanced
    public WebClient.Builder loadBalancedWebClientBuilder() {
        return WebClient.builder();
    }
}
