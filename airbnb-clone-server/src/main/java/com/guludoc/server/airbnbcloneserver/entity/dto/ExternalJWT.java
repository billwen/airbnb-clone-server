package com.guludoc.server.airbnbcloneserver.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ExternalJWT {

    // OAuth2 provider
    private String provider;

    private String accessToken;

    private String refreshToken;
}
