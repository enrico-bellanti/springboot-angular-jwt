package com.baseApp.backend.payloads.requests;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class SignInRequest {
    @NotBlank(message = "email_required")
    @Email
    private String email;

    @NotBlank(message = "password_required")
    @Size(min = 6, max = 40)
    private String password;
}
