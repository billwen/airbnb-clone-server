package com.guludoc.server.airbnbcloneserver.entity.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OAuth2AuthorizationCode {

    // OAuth2 provider
    @NotEmpty
    private String provider;

    @NotEmpty
    private String authorizationCode;

}
