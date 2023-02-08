package com.baseApp.backend.payloads.requests;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class PermissionRequest {
    @NotBlank(message = "permission_required")
    @Size(min = 3, max = 50)
    private String name;

    @NotBlank(message = "permission_category_required")
    @Size(min = 3, max = 30)
    private String category;
}
