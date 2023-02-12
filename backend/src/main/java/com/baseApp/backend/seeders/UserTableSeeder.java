package com.baseApp.backend.seeders;

import com.baseApp.backend.exceptions.RoleException;
import com.baseApp.backend.models.Role;
import com.baseApp.backend.models.User;
import com.baseApp.backend.seeders.models.UserModelSeeder;
import com.baseApp.backend.services.RoleService;
import com.baseApp.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserTableSeeder extends BaseSeeder<UserModelSeeder> {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    public UserTableSeeder() {
        super(UserModelSeeder.class, "classpath:seeders/users.json");
    }


    @Override
    public void seed(List<UserModelSeeder> list) {
        list.forEach(us -> {
            Set<Role> roles = us.getRoles().stream().map(name -> {
                return  roleService.getByName(name).orElseThrow(
                        () -> new RoleException("role_not_found")
                );
            }).collect(Collectors.toSet());

            var user = us.build();
            user.setRoles(roles);
            userService.insertOrUpdate(user);
        });
    }
}
