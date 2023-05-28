package com.guludoc.server.airbnbcloneserver.api;

import com.guludoc.server.airbnbcloneserver.entity.Account;
import com.guludoc.server.airbnbcloneserver.service.DataAccessService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class HomeController {

    private final DataAccessService dataAccessService;

    @PreAuthorize("hasAuthority('SCOPE_USER')")
    @GetMapping("/me")
    public ResponseEntity<Account> me(Authentication authentication) {
        String username = authentication.getName();
        Account account = dataAccessService.findAccountByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username + " doesn't exist."));

        return new ResponseEntity<>(account, HttpStatus.OK);
    }
}
