package org.ipan.restaurant.gateway.filters;

import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.jwk.source.RemoteJWKSet;
import com.nimbusds.jose.proc.DefaultJOSEObjectTypeVerifier;
import com.nimbusds.jose.proc.JWSKeySelector;
import com.nimbusds.jose.proc.JWSVerificationKeySelector;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTClaimsVerifier;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import jakarta.ws.rs.core.HttpHeaders;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.setResponseStatus;

public class JwtAuthHeaderGlobalFilter implements GlobalFilter {

    private final String issuer = "http://doppler:9000/auth";

    private final ConfigurableJWTProcessor<SecurityContext> jwtProcessor;
    private final String redirectToLoginUrl;

    public JwtAuthHeaderGlobalFilter(String jwkUrl, String redirectToLoginUrl) throws MalformedURLException {
        this.redirectToLoginUrl = redirectToLoginUrl;
        jwtProcessor = new DefaultJWTProcessor<>();

        // Set the required "typ" header "at+jwt" for access tokens issued by the
        // Connect2id server, may not be set by other servers
        // jwtProcessor.setJWSTypeVerifier(new DefaultJOSEObjectTypeVerifier<>(JOSEObjectType.JWT));

        // The public RSA keys to validate the signatures will be sourced from the
        // OAuth 2.0 server's JWK set, published at a well-known URL. The RemoteJWKSet
        // object caches the retrieved keys to speed up subsequent look-ups and can
        // also handle key-rollover
        JWKSource<SecurityContext> keySource = new RemoteJWKSet<>(new URL(jwkUrl));

        // The expected JWS algorithm of the access tokens (agreed out-of-band)
        JWSAlgorithm expectedJWSAlg = JWSAlgorithm.RS256;

        // Configure the JWT processor with a key selector to feed matching public
        // RSA keys sourced from the JWK set URL
        JWSKeySelector<SecurityContext> keySelector = new JWSVerificationKeySelector<>(expectedJWSAlg, keySource);

        jwtProcessor.setJWSKeySelector(keySelector);

        // Set the required JWT claims for access tokens issued by the Connect2id
        // server, may differ with other servers
        jwtProcessor.setJWTClaimsSetVerifier(new DefaultJWTClaimsVerifier(new JWTClaimsSet.Builder().issuer(issuer).build(), new HashSet<>(Arrays.asList("sub", "iat", "exp"))));
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        AntPathMatcher pathMatcher = new AntPathMatcher();

        String path = exchange.getRequest().getPath().value();
        if (pathMatcher.match("/auth/login", path)) {
            return chain.filter(exchange);
        }

        if (!exchange.getResponse().isCommitted()) {
            List<String> values = exchange.getRequest().getHeaders()
                    .getOrDefault(HttpHeaders.AUTHORIZATION,Collections.emptyList());

            if (!values.isEmpty()) {
                String headerValue = values.iterator().next();
                String[] parts = headerValue.split(" ");
                if ("Bearer".equals(parts[0]) && parts[1] != null) {
                    try {
                        jwtProcessor.process(parts[1], null);
                        return chain.filter(exchange);
                    } catch (Exception e) {
                        System.err.println("JWT is invalid, it requires refresh!");
                    }
                }
            }

            setResponseStatus(exchange, HttpStatus.FOUND);

            final ServerHttpResponse response = exchange.getResponse();
            response.getHeaders().set(org.springframework.http.HttpHeaders.LOCATION, redirectToLoginUrl);
            return response.setComplete();
        }

        return Mono.empty();
    }
}
