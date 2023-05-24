package com.guludoc.server.airbnbcloneserver.config;

import com.guludoc.server.airbnbcloneserver.security.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Map;

@Configuration
@EnableWebSecurity
public class DefaultSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        String defaultEncoder = "bcrypt";
        Map<String, PasswordEncoder> encoders = Map.of(
                defaultEncoder, new BCryptPasswordEncoder(),
                "SHA-1", new MessageDigestPasswordEncoder("SHA-1"),
                "noop", NoOpPasswordEncoder.getInstance()
        );

        return new DelegatingPasswordEncoder(defaultEncoder, encoders);
    }

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(auth -> auth.requestMatchers("/error").permitAll()
                .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults());

        return http.build();
    }

    /**
     * The WebSecurityCustomizer is a callback interface that can be used to customize WebSecurity.
     * @return WebSecurityCustomizer
     */
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> {
            web.ignoring()
                    .requestMatchers("/public/**");
        };
    }
}
