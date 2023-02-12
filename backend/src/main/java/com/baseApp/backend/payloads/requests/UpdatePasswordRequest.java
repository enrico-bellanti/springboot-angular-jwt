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
public class UpdatePasswordRequest {
    @NotBlank(message = "password_required")
    @Size(min = 6, max = 40)
    private String password;

    @NotBlank(message = "confirmed_password_required")
    @Size(min = 6, max = 40)
    private String confirmed_password;
}
