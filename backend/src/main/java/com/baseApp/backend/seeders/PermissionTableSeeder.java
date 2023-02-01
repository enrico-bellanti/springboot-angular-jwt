package com.baseApp.backend.seeders;

import com.baseApp.backend.models.Permission;
import com.baseApp.backend.services.PermissionService;
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

@Service
public class PermissionTableSeeder extends BaseSeeder<Permission> {

    @Autowired
    private PermissionService permissionService;

    public PermissionTableSeeder() {
        super(Permission.class, "classpath:seeders/permissions.json");
    }


    @Override
    public void seed(List<Permission> list) {
        list.forEach(permissionService::insertOrUpdate);
    }
}
