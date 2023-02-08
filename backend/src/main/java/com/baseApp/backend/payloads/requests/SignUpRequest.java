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
public class SignUpRequest {
    @NotBlank(message = "first_name_required")
    @Size(max = 60)
    private String firstName;

    @NotBlank(message = "last_name_required")
    @Size(max = 60)
    private String lastName;

    @NotBlank(message = "email_required")
    @Email
    private String email;

    @NotBlank(message = "password_required")
    @Size(min = 6, max = 40)
    private String password;
}
