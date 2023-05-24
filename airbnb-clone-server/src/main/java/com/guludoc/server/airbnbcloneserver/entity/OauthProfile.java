package com.guludoc.server.airbnbcloneserver.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

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

    @Column(name = "oauth_username", length = 128, nullable = false)
    private String oauthUsername;

    @Column(name = "access_key", length = 512)
    private String accessKey;

    @Column(name = "refresh_key", length = 512)
    private String refreshKey;

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
