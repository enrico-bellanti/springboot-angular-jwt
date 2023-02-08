package com.baseApp.backend.payloads.responses;

import com.baseApp.backend.models.Permission;
import com.baseApp.backend.models.Role;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Setter
@Getter
@ToString
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
