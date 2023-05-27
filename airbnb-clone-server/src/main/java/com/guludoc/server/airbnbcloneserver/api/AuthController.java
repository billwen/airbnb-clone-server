package com.guludoc.server.airbnbcloneserver.api;

import com.guludoc.server.airbnbcloneserver.entity.Account;
import com.guludoc.server.airbnbcloneserver.entity.dto.SignupRequest;
import com.guludoc.server.airbnbcloneserver.service.DataAccessService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final DataAccessService dataAccessService;

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
    public ResponseEntity<Pair<String, Optional<Account>>> register(@RequestBody SignupRequest signupRequest) {

        try {
            Account account = dataAccessService.signup(signupRequest);
            return new ResponseEntity<>(Pair.of("ok", Optional.of(account)), HttpStatus.OK);
        } catch (UsernameNotFoundException e) {
            return new ResponseEntity<>(Pair.of(e.getLocalizedMessage(), Optional.empty()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/user/signup")
    public ResponseEntity<String> signIn() {
        return new ResponseEntity<>("Signed in", HttpStatus.OK);
    }
}
