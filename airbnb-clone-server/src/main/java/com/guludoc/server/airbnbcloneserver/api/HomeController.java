package com.guludoc.server.airbnbcloneserver.api;

import com.guludoc.server.airbnbcloneserver.entity.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
public class HomeController {

    @PreAuthorize("hasAuthority('SCOPE_mod_custom')")
    @GetMapping("/me")
    public ResponseEntity<Account> me(Authentication authentication) {
        Account account = (Account) authentication.getDetails();
        return new ResponseEntity<>(account, HttpStatus.OK);
    }
}
