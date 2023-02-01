package com.baseApp.backend.seeders.payloads.responses;

import com.baseApp.backend.models.Permission;
import com.baseApp.backend.models.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleResponse {

    private UUID id;
    private String name;
    private Set<Permission> permissions;

    public RoleResponse(Role role) {
        this.id = role.getId();
        this.name = role.getName();
        this.permissions = role.getPermissions();
    }

}
