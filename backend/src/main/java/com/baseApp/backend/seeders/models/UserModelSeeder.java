package com.baseApp.backend.seeders.models;

import com.baseApp.backend.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Locale;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserModelSeeder {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Boolean isEnabled;
    private String phone;
    private Locale preferredLang;
    private Set<String> roles;

    public User build(){
        return User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .password(password)
                .isEnabled(isEnabled)
                .phone(phone)
                .preferredLang(preferredLang)
                .build();
    }
}
