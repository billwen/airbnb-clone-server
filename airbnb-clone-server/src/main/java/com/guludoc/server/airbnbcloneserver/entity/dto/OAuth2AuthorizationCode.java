package com.guludoc.server.airbnbcloneserver.entity.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OAuth2AuthorizationCode {

    // OAuth2 provider
    @NotEmpty
    private String provider;

    @NotEmpty
    private String code;

    @NotEmpty
    private String scope;

    public List<String> scopes() {
        return List.of(StringUtils.split(this.scope, " "));
    }

}
