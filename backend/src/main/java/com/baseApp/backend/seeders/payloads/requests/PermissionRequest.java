package com.baseApp.backend.seeders.payloads.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PermissionRequest {
    @NotBlank(message = "permission_required")
    @Size(min = 3, max = 50)
    private String name;

    @NotBlank(message = "permission_category_required")
    @Size(min = 3, max = 30)
    private String category;
}
