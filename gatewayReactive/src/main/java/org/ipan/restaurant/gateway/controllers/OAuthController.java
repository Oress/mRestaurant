package org.ipan.restaurant.gateway.controllers;

import org.ipan.restaurant.gateway.AppUriBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Controller
public class OAuthController {
    private final String clientSecret = "secret";
    private final String redirectUri = "http://127.0.0.1:8001/login/oauth2/code/gateway-client";

    @LoadBalanced
    private final WebClient.Builder webClient;

    @Autowired
    public OAuthController(WebClient.Builder webClient) {
        this.webClient = webClient;
    }

    @GetMapping(value = "/login/oauth2/code/gateway-client", produces = "application/json")
    @ResponseBody
    public Mono<ResponseEntity<String>> getAccessTokenByCode(@RequestParam("code") String code) {
        String uri = AppUriBuilder.getAccessTokenByCodeUri(code, redirectUri);

        return webClient.build().post().uri(uri).headers(headers -> headers.setBasicAuth("api-gateway", clientSecret))
                .exchangeToMono(response -> response.bodyToMono(String.class))
                .map(body -> ResponseEntity.ok(body));
    }

    @GetMapping(value = "/auth/login/onsuccess")
    public Mono<ResponseEntity<Void>> requestCodeOnSuccessfulLogin(@RequestHeader("Cookie") String cookie) { // including JSESSIONID
        String uri = AppUriBuilder.requestOAuthCodeUri(redirectUri);

        return webClient.build().get().uri(uri).headers(httpHeaders -> httpHeaders.set("Cookie", cookie))
                .exchangeToMono(response -> response.toBodilessEntity());
    }
}
