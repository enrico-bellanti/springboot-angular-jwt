package com.baseApp.backend.seeders;

import com.baseApp.backend.exceptions.RoleException;
import com.baseApp.backend.models.Role;
import com.baseApp.backend.models.User;
import com.baseApp.backend.seeders.models.UserSeeder;
import com.baseApp.backend.services.RoleService;
import com.baseApp.backend.services.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TestTableSeeder extends BaseSeeder<UserSeeder>{
    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    public TestTableSeeder() {
        super(UserSeeder.class, "classpath:seeders/users.json");
    }

    @Override
    public void seed(List<UserSeeder> userSeederList) {
        userSeederList.forEach(us -> {
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
