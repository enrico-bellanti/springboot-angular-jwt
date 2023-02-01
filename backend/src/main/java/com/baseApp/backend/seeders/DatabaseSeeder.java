package com.baseApp.backend.seeders;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DatabaseSeeder {
    @Value("${database.run-seeders}")
    private boolean seedersOn;

    @Autowired
    PermissionTableSeeder permissionTableSeeder;

    @Autowired
    RoleTableSeeder roleTableSeeder;

    @Autowired
    UserTableSeeder userTableSeeder;


    @EventListener
    public void seed(ContextRefreshedEvent event) {
        if (seedersOn){
            log.info("Seeders run...");
            permissionTableSeeder.run();
            roleTableSeeder.run();
            userTableSeeder.run();
            log.info("Seeders finished");
        }

    }
}
