package com.guludoc.server.airbnbcloneserver.repo;

import com.guludoc.server.airbnbcloneserver.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
