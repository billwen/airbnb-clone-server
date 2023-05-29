package com.guludoc.server.airbnbcloneserver.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "oauth_profile")
public class OauthProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 32, nullable = false)
    private String provider;

    // Google email
    @Column(name = "oauth_username", length = 128, nullable = false)
    private String oauthUsername;

    @Column(name = "oauth_sub", length = 128)
    private String oauthSub;

    @Column(name = "person_name", length = 128)
    private String personName;

    @Column(name = "avatar", length = 256)
    private String avatarUrl;

    @Column(name = "access_key", length = 1024)
    private String accessKey;

    @Column(name = "refresh_key", length = 1024)
    private String refreshKey;

    @Column(length = 512)
    private String scope;

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private Date updatedAt;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;
}
