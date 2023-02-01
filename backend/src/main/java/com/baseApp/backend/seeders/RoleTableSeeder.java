package com.baseApp.backend.seeders;


import com.baseApp.backend.exceptions.PermissionException;
import com.baseApp.backend.models.Permission;
import com.baseApp.backend.models.Role;
import com.baseApp.backend.seeders.models.RoleSeeder;
import com.baseApp.backend.services.PermissionService;
import com.baseApp.backend.services.RoleService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class RoleTableSeeder extends BaseSeeder<RoleSeeder> {

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    public RoleTableSeeder() {
        super(RoleSeeder.class, "classpath:seeders/roles.json");
    }

    @Override
    public void seed(List<RoleSeeder> list) {
        list.forEach(rs -> {
            Set<Permission> permissions = rs.getPermissions().stream().map(name -> {
                return permissionService.getByName(name).orElseThrow(
                        () -> new PermissionException("permission_not_found")
                );
            }).collect(Collectors.toSet());
            var role = new Role(
                    rs.getName(),
                    permissions
            );
            roleService.insertOrUpdate(role);
        });
    }
}
