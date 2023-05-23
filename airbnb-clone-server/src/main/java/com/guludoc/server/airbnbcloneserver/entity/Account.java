package com.guludoc.server.airbnbcloneserver.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "account")
public class Account implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 64)
    private String username;

    @Column(length = 128)
    private String email;

    @Column(name = "hashed_passwd", length = 128)
    private String password;

    @Column(name = "expired", nullable = false)
    private boolean accountNonExpired;

    @Column(name = "locked", nullable = false)
    private boolean accountNonLocked;

    @Column(name = "passwd_expired", nullable = false)
    private boolean credentialsNonExpired;

    @Column(nullable = false)
    private boolean enabled;

    @ManyToMany
    @Fetch(FetchMode.JOIN)
    @JoinTable(name = "int_accounts_roles",
            joinColumns = {@JoinColumn(name = "account_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")}
    )
    private Set<Role> authorities;
}
