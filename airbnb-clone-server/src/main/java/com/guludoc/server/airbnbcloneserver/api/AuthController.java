package com.guludoc.server.airbnbcloneserver.api;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @GetMapping(value = "/signin")
    public ResponseEntity<String> login(HttpServletRequest request, HttpServletResponse response) {
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
}
