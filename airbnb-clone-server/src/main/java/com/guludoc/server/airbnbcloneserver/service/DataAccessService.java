package com.guludoc.server.airbnbcloneserver.service;

import com.guludoc.server.airbnbcloneserver.entity.Account;
import com.guludoc.server.airbnbcloneserver.entity.Role;
import com.guludoc.server.airbnbcloneserver.entity.dto.SignupRequest;
import com.guludoc.server.airbnbcloneserver.repo.AccountRepository;
import com.guludoc.server.airbnbcloneserver.repo.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Component
public class DataAccessService {
    private final AccountRepository accountRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    public Role findOrCreateRoleByAuthority(String authority) {
        return roleRepository.findByAuthority(authority).orElse( roleRepository.save(Role.of(authority)));
    }

    public Account signup(SignupRequest request) throws UsernameNotFoundException {
        Role defaultRole = findOrCreateRoleByAuthority("USER");

        // Check username and email
        Optional<Account> sameName = accountRepository.findByUsername(request.getName());
        Optional<Account> sameEmail = accountRepository.findByEmail(request.getEmail());
        if (sameName.isPresent() || sameEmail.isPresent()) {
            log.warn("Username {} or Email {} already exists", request.getName(), request.getEmail());
            throw new UsernameNotFoundException("Username " + request.getName() + " or Email " + request.getEmail() + " already exists");
        }

        // Create a new user
        Account account = Account.of(
                Set.of(defaultRole),
                request.getName(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                "jwt"
                );
        return accountRepository.save(account);
    }

    public Optional<Account> findAccountByUsername(String username) {
        return accountRepository.findByUsername(username);
    }
}
