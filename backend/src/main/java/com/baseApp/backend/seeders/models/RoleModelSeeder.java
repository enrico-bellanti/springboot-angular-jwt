package com.baseApp.backend.seeders.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RoleModelSeeder {
    private String name;
    private Set<String> permissions = new HashSet<>();
}
