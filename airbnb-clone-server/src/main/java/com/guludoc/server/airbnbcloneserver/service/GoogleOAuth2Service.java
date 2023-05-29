package com.guludoc.server.airbnbcloneserver.service;

import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.auth.oauth2.TokenResponseException;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.guludoc.server.airbnbcloneserver.config.AppAuthParam;
import com.guludoc.server.airbnbcloneserver.entity.OauthProfile;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.InvalidParameterException;

@Slf4j
@RequiredArgsConstructor
@Component
public class GoogleOAuth2Service {

    private final String PROVIDER_GOOGLE_NAME = "google";

    private final AppAuthParam authParam;

    public String requestAccessToken(@NotEmpty String code) throws IOException, InvalidParameterException {

        AppAuthParam.OAuth2Provider googleConfig = authParam.getProviders().get(PROVIDER_GOOGLE_NAME);
        if ( googleConfig == null || googleConfig.getClientId() == null || googleConfig.getClientSecret() == null) {
            throw new InvalidParameterException("Google configuration is missing");
        }

        try {
            log.trace("Redirect url: {}", googleConfig.getRedirectUrl());
            GoogleTokenResponse response = new GoogleAuthorizationCodeTokenRequest(
                    new NetHttpTransport(),
                    new GsonFactory(),
                    googleConfig.getClientId(),
                    googleConfig.getClientSecret(),
                    code,
                    googleConfig.getRedirectUrl()
            ).execute();

            GoogleIdToken googleId = GoogleIdToken.parse(new GsonFactory(), response.getIdToken());

            OauthProfile profile = OauthProfile.builder()
                    .accessKey(response.getAccessToken())
                    .refreshKey(response.getRefreshToken())
                    .oauthUsername(googleId.getPayload().getEmail())
                    .oauthSub(googleId.getPayload().getSubject())
                    .avatarUrl((String) googleId.getPayload().get("picture"))
                    .personName((String) googleId.getPayload().get("name"))
                    .build();
            return response.getAccessToken();

        } catch (TokenResponseException e) {
            if (e.getDetails() != null) {
                log.error("Error: {} - {}, Details: {}, Url: {}",
                        e.getMessage(),
                        e.getDetails().getError(),
                        e.getDetails().getErrorDescription(),
                        e.getDetails().getErrorUri());

            } else {
                log.error("Error: {}", e.getMessage());
            }

            throw new IOException("Get access token failed");
        }
    }
}
