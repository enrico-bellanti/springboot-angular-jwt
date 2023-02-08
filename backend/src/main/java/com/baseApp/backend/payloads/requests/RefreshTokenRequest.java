package com.baseApp.backend.payloads.requests;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class RefreshTokenRequest {
    @NotNull(message = "refresh_token_required")
    private UUID refreshToken;
}
