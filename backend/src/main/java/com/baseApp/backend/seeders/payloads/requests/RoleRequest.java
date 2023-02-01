package com.baseApp.backend.seeders.payloads.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RoleRequest {

    @NotBlank(message = "role_required")
    @Size(min = 3, max = 50)
    private String name;

    private Set<UUID> permissions = new HashSet<>();
}
