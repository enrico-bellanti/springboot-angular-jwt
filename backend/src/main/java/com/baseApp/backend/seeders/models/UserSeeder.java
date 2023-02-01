package com.baseApp.backend.seeders.models;

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
public class UserSeeder {

    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String phone;
    private Locale preferredLang;
    private Set<String> roles;

}
