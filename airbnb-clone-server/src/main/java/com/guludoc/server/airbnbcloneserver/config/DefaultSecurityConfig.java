package com.guludoc.server.airbnbcloneserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Map;

@Configuration
@EnableWebSecurity
public class DefaultSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        String defaultEncoder = "bcrypt";
        Map<String, PasswordEncoder> encoders = Map.of(
                defaultEncoder, new BCryptPasswordEncoder(),
                "sha-1", new MessageDigestPasswordEncoder("SHA-256")
        );

        return new DelegatingPasswordEncoder(defaultEncoder, encoders);
    }
}
