package com.guludoc.server.airbnbcloneserver.config;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Map;

@Slf4j
public class DefaultSecurityConfigTest {

    @Test
    public void all() {
        for ( int workfactor = 4; workfactor < 31; workfactor++) {
            generateWorkfactor(workfactor);
        }
    }
    private void generateWorkfactor(int workfactor) {
        PasswordEncoder encoder = passwordEncoder(workfactor);
        long start = System.currentTimeMillis();
        String encoded = encoder.encode("password");
        log.info(encoded);
        long stop = System.currentTimeMillis();
        log.info("It took "+ (stop - start) + " ms for workfactor of " + workfactor);
    }

    private DelegatingPasswordEncoder passwordEncoder(int workfactor) {
        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder(workfactor);
        return new DelegatingPasswordEncoder("bcrypt", Map.of("bcrypt", bcrypt));
    }
}
