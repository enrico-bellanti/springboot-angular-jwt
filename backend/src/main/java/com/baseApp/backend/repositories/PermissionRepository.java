package com.baseApp.backend.repositories;

import com.baseApp.backend.models.Permission;
import com.baseApp.backend.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, UUID> {
    Optional<Permission> findByName(String name);

    Boolean existsByName(String username);
}
