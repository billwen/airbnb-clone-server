package com.guludoc.server.airbnbcloneserver.service;

import com.guludoc.server.airbnbcloneserver.config.AppAuthParam;
import com.guludoc.server.airbnbcloneserver.entity.Account;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class JWTUtil {

    private final AppAuthParam authParam;

    private final JwtEncoder encoder;

    public String accessToken(@NotNull Authentication authentication) {

        Instant now = Instant.now();
        Instant expiry = now.plusSeconds(authParam.getJwt().getAccessTokenExpiryInSeconds());
        String scope = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject(authentication.getName())
                .issuer(authParam.getJwt().getIss())
                .claim("scope", scope)
                .issuedAt(now)
                .expiresAt(expiry)
                .build();

        return encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

}
