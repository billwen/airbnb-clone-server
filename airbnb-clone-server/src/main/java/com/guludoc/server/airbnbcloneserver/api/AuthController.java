package com.guludoc.server.airbnbcloneserver.api;

import com.guludoc.server.airbnbcloneserver.config.AppAuthParam;
import com.guludoc.server.airbnbcloneserver.entity.Account;
import com.guludoc.server.airbnbcloneserver.entity.dto.OAuth2AuthorizationCode;
import com.guludoc.server.airbnbcloneserver.entity.dto.SignupRequest;
import com.guludoc.server.airbnbcloneserver.service.DataAccessService;
import com.guludoc.server.airbnbcloneserver.service.GoogleOAuth2Service;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.time.Instant;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final DataAccessService dataAccessService;

    private final JwtEncoder encoder;

    private final AppAuthParam authParam;

    private final GoogleOAuth2Service google;

    @GetMapping(value = "/signin")
    public ResponseEntity<String> login() {
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    /**
     * Sign up a user
     * @param signupRequest Signin request
     * @return Pair<String, Optional<Account>>
     */
    @PostMapping(value = "/signup")
    public ResponseEntity<Pair<String, Optional<Account>>> register(@Valid @RequestBody SignupRequest signupRequest) {

        try {
            Account account = dataAccessService.signup(signupRequest);
            return new ResponseEntity<>(Pair.of("ok", Optional.of(account)), HttpStatus.OK);
        } catch (UsernameNotFoundException e) {
            return new ResponseEntity<>(Pair.of(e.getLocalizedMessage(), Optional.empty()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/signup")
    public ResponseEntity<String> signIn() {
        return new ResponseEntity<>("Signed in", HttpStatus.OK);
    }

    /**
     * Local user to JWT token
     * @param authentication Web
     * @return token
     */
    @PostMapping("/token")
    public String token(Authentication authentication) {
        Instant now = Instant.now();
        long expiry = 36000L;
        String scope = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("Gang")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiry))
                .subject(authentication.getName())
                .claim("scope", scope)
                .build();

        return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    /**
     * Exchange external OAuth2 token for local generated token
     * @param jwt
     * @return String
     */
    @PostMapping("/social")
    public ResponseEntity<String> oauth2Token(@Valid @RequestBody OAuth2AuthorizationCode jwt) throws IOException, InvalidParameterException {

        // Get access token
        String accessCode = google.requestAccessToken(jwt.getCode());
        return new ResponseEntity<>(accessCode, HttpStatus.OK);
    }
}
