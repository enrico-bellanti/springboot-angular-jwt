package com.baseApp.backend.services;

import com.baseApp.backend.exceptions.PermissionException;
import com.baseApp.backend.exceptions.RoleException;
import com.baseApp.backend.models.Role;
import com.baseApp.backend.payloads.requests.RoleRequest;
import com.baseApp.backend.payloads.responses.RoleResponse;
import com.baseApp.backend.repositories.PermissionRepository;
import com.baseApp.backend.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;


    public Page<RoleResponse> getAll(PageRequest pageRequest){
        return roleRepository.findAll(pageRequest)
                .map(r -> new RoleResponse(r));
    }


    public Optional<Role> getByName(String name){
        return roleRepository.findByName(name);
    }

    public RoleResponse create(RoleRequest roleRequest){
        String roleName = roleRequest.getName();

        if (roleRepository.existsByName(roleName)) {
            throw new RoleException("role_already_exist", roleName);
        }

        var role = new Role(
                roleName
        );

        if (!roleRequest.getPermissions().isEmpty()){
            var permissions = roleRequest.getPermissions().stream().map(id ->
                    permissionRepository.findById(id)
                            .orElseThrow(() -> new PermissionException("permission_id_not_found", id.toString()))
            ).collect(Collectors.toSet());

            role.setPermissions(permissions);
        }

        return new RoleResponse(roleRepository.save(role));
    }

    public void hardDelete(UUID id){

        var role = roleRepository.findById(id).orElseThrow(
                () -> new RoleException("role_id_not_found", id.toString())
        );

        roleRepository.delete(role);
    }

    @Transactional
    public Role addPermission(UUID roleId, UUID permissionId){
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RoleException("role_id_not_found", roleId.toString()));

        var permission = permissionRepository.findById(permissionId)
                .orElseThrow(() -> new PermissionException("permission_id_not_found", permissionId.toString()));

        role.addPermission(permission);

        return role;
    }

    @Transactional
    public Role removePermission(UUID roleId, UUID permissionId){

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RoleException("role_id_not_found", roleId.toString()));

        var permission = permissionRepository.findById(permissionId)
                .orElseThrow(() -> new PermissionException("permission_id_not_found", permissionId.toString()));

        role.removePermission(permission);

        return role;
    }

    public Role managePermission(UUID roleId, Set<UUID> permissionsId){
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RoleException("role_id_not_found", roleId.toString()));

        var permissions = permissionsId.stream().map(id ->
                permissionRepository.findById(id)
                        .orElseThrow(() -> new PermissionException("permission_id_not_found", id.toString()))
        ).collect(Collectors.toSet());

        role.setPermissions(permissions);

        return roleRepository.save(role);

    }

    public Role insertOrUpdate(Role role){

        var optionalRole = roleRepository.findByName(role.getName());

        if (optionalRole.isPresent()){
            Role existingRole = optionalRole.get();
            existingRole.setName(role.getName());
            existingRole.setPermissions(role.getPermissions());
            return roleRepository.save(existingRole);
        } else {
            return roleRepository.save(role);
        }
    }

}
