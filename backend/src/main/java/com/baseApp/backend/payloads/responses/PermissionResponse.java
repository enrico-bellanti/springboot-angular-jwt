package com.baseApp.backend.payloads.responses;

import com.baseApp.backend.models.Permission;
import lombok.*;

import java.util.UUID;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PermissionResponse {
    private UUID id;
    private String name;
    private String category;

    public PermissionResponse(Permission permission) {
        this.id = permission.getId();
        this.name = permission.getName();
        this.category = permission.getCategory();
    }
}
