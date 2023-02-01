package com.baseApp.backend.seeders;

import com.baseApp.backend.exceptions.PermissionException;
import com.baseApp.backend.exceptions.RoleException;
import com.baseApp.backend.exceptions.UserException;
import com.baseApp.backend.models.Permission;
import com.baseApp.backend.models.Role;
import com.baseApp.backend.models.User;
import com.baseApp.backend.seeders.models.RoleSeeder;
import com.baseApp.backend.seeders.models.UserSeeder;
import com.baseApp.backend.services.RoleService;
import com.baseApp.backend.services.UserService;
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
public class UserTableSeeder extends BaseSeeder<UserSeeder> {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    public UserTableSeeder() {
        super(UserSeeder.class, "classpath:seeders/users.json");
    }


    @Override
    public void seed(List<UserSeeder> list) {
        list.forEach(us -> {
            Set<Role> roles = us.getRoles().stream().map(name -> {
                return  roleService.getByName(name).orElseThrow(
                        () -> new RoleException("role_not_found")
                );
            }).collect(Collectors.toSet());

            var user = User.builder()
                    .firstName(us.getFirstName())
                    .lastName(us.getLastName())
                    .email(us.getEmail())
                    .password(us.getPassword())
                    .phone(us.getPhone())
                    .preferredLang(us.getPreferredLang())
                    .roles(roles)
                    .build();
            userService.insertOrUpdate(user);
        });
    }
}
