package com.guludoc.server.airbnbcloneserver.repo;

import com.guludoc.server.airbnbcloneserver.entity.OauthProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OauthProfileRepository extends JpaRepository<OauthProfile, Long> {

    List<OauthProfile> findByAccount_Username(String username);
}
