package com.baseApp.backend.payloads.responses;

import com.baseApp.backend.models.Role;
import lombok.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    private UUID id;
    private String email;
    private String accessToken;
    private String type = "Bearer";
    private UUID refreshToken;
    private Set<Role> roles;

    public AuthenticationResponse(UUID id, String email, Set<Role> roles, String accessToken, UUID refreshToken) {
        this.id = id;
        this.email = email;
        this.roles = roles;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
