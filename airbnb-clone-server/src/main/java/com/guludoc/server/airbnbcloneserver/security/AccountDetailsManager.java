package com.guludoc.server.airbnbcloneserver.security;

import com.guludoc.server.airbnbcloneserver.repo.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AccountDetailsManager implements UserDetailsService {

    private final AccountRepository accountRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return accountRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username + " doesnot exist."));
    }
}
