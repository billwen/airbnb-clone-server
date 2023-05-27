package com.guludoc.server.airbnbcloneserver.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
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

    @JsonIgnore
    @Column(name = "hashed_passwd", length = 128)
    private String password;

    @JsonIgnore
    @Column(name = "no_expired", nullable = false)
    private boolean accountNonExpired;

    @JsonIgnore
    @Column(name = "no_locked", nullable = false)
    private boolean accountNonLocked;

    @JsonIgnore
    @Column(name = "no_passwd_expired", nullable = false)
    private boolean credentialsNonExpired;

    @JsonIgnore
    @Column(nullable = false)
    private boolean enabled;

    @JsonIgnore
    @Column(name = "jwt_key", length = 128)
    private String jwtKey;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;

    @ManyToMany
    @Fetch(FetchMode.JOIN)
    @JoinTable(name = "int_accounts_roles",
            joinColumns = {@JoinColumn(name = "account_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")}
    )
    private Set<Role> authorities;

    @OneToMany(mappedBy = "account")
    private Set<OauthProfile> oauthProfiles;

    public static Account of(Set<Role> roles, String username, String email, String hashed_password, String jwtKey) {
        Account account = new Account();
        account.setUsername(username);
        account.setEmail(email);
        account.setPassword(hashed_password);
        account.setJwtKey(jwtKey);
        account.setAuthorities(roles);

        // Default values
        account.setAccountNonExpired(true);
        account.setAccountNonLocked(true);
        account.setCredentialsNonExpired(true);
        account.setEnabled(true);

        return account;
    }
}
