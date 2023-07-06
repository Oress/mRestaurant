package org.ipan.restaurant.gateway;

import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class AppUriBuilder {

    public static String getAccessTokenByCodeUri(String code, String redirectUri) {
        String uri = UriComponentsBuilder.newInstance().scheme("lb").host("auth-service")
                .path("/auth/oauth2/token")
                .queryParam("grant_type", "authorization_code")
                .queryParam("code", code)
                .queryParam("redirect_uri", redirectUri)
                .build()
                .toUriString();

        return uri;
    }


    public static String requestOAuthCodeUri(String redirectUri) {
        String uri = UriComponentsBuilder.newInstance().scheme("lb").host("auth-service")
                .path("/auth/oauth2/authorize")
                .queryParam("response_type", "code")
                .queryParam("client_id", "api-gateway")
                .queryParam("redirect_uri", redirectUri)
                .build()
                .toUriString();

        return uri;
    }

    public static String loginPageUri() {
        return "http://127.0.0.1:8001/auth/login";
    }

    public static String publicKeyUrl() {
        return "http://127.0.0.1:9000/auth/oauth2/jwks";
    }
}
