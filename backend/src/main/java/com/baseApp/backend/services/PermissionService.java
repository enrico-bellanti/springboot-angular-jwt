package com.baseApp.backend.services;

import com.baseApp.backend.exceptions.PermissionException;
import com.baseApp.backend.models.Permission;
import com.baseApp.backend.payloads.requests.PermissionRequest;
import com.baseApp.backend.payloads.responses.PermissionResponse;
import com.baseApp.backend.repositories.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    public Page<PermissionResponse> getAll(PageRequest pageRequest){
        return permissionRepository.findAll(pageRequest)
                .map(p -> new PermissionResponse(p));
    }

    public Optional<Permission> getByName(String name){
        return permissionRepository.findByName(name);
    }

    public PermissionResponse create(PermissionRequest permissionRequest){
        var permissionName = permissionRequest.getName();
        
        if (permissionRepository.existsByName(permissionName)) {
            throw new PermissionException("permission_already_exist", permissionName);
        }

        var permission = new Permission(
                permissionName,
                permissionRequest.getCategory()
        );

        return new PermissionResponse(permissionRepository.save(permission));
    }

    public void hardDelete(UUID id){

        var permission = permissionRepository.findById(id).orElseThrow(
                () -> new PermissionException("permission_not_found", id.toString())
        );

        permissionRepository.delete(permission);
    }

    public Permission insertOrUpdate(Permission permission){

        var optionalPermission = permissionRepository.findByName(permission.getName());

        if (optionalPermission.isPresent()){
            Permission existingPermission = optionalPermission.get();
            existingPermission.setName(permission.getName());
            existingPermission.setCategory(permission.getCategory());
            return permissionRepository.save(existingPermission);
        } else {
            return permissionRepository.save(permission);
        }
    }


}
