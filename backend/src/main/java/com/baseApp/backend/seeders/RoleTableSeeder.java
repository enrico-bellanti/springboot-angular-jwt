package com.baseApp.backend.seeders;


import com.baseApp.backend.exceptions.PermissionException;
import com.baseApp.backend.models.Permission;
import com.baseApp.backend.models.Role;
import com.baseApp.backend.seeders.models.RoleModelSeeder;
import com.baseApp.backend.services.PermissionService;
import com.baseApp.backend.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class RoleTableSeeder extends BaseSeeder<RoleModelSeeder> {

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    public RoleTableSeeder() {
        super(RoleModelSeeder.class, "classpath:seeders/roles.json");
    }

    @Override
    public void seed(List<RoleModelSeeder> list) {
        list.forEach(rs -> {
            Set<Permission> permissions = rs.getPermissions().stream().map(name -> {
                return permissionService.getByName(name).orElseThrow(
                        () -> new PermissionException("permission_not_found", name)
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
