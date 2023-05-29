package com.guludoc.server.airbnbcloneserver.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;

@Data
@Component
@ConfigurationProperties(prefix = "app.auth")
public class AppAuthParam {

    private Jwt jwt;
    private Map<String, OAuth2Provider> providers;

    @Data
    static public class Jwt {
        private String iss;
        private RSAPrivateKey privateKey;
        private RSAPublicKey publicKey;
        private Integer accessTokenExpiryInSeconds;
        private Integer refreshTokenExpiryInHours;
    }

    @Data
    static public class OAuth2Provider {
        private String clientId;
        private String clientSecret;
        private String redirectUrl;
    }
}
