package com.baseApp.backend.controllers;

import com.baseApp.backend.seeders.payloads.requests.UUIDListRequest;
import com.baseApp.backend.seeders.payloads.requests.RoleRequest;
import com.baseApp.backend.seeders.payloads.responses.BodyResponse;
import com.baseApp.backend.seeders.payloads.responses.MessageResponse;
import com.baseApp.backend.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.UUID;

import static com.baseApp.backend.utils.TranslateUtils.tl;

@RestController
@RequestMapping("/api/v1/role")
@RequiredArgsConstructor
public class RoleController {
    @Autowired
    private final RoleService roleService;

    @GetMapping
    public ResponseEntity<BodyResponse> list(
            @RequestParam(value = "column", required = false, defaultValue = "id") String column,
            @RequestParam(value = "direction", required = false, defaultValue = "ASC") Sort.Direction direction,
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "25") Integer size
    ) {
        Sort sort = Sort.by(direction, column);
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        var bodyResponse = BodyResponse.builder()
                .message(tl("role_list_success"))
                .data(roleService.getAll(pageRequest))
                .build();

        return ResponseEntity.ok().body(bodyResponse);

    }

    @PostMapping
    public ResponseEntity<BodyResponse> create(@Valid @RequestBody RoleRequest roleRequest) {

        var bodyResponse = new BodyResponse(
                tl("role_created_success"),
                roleService.create(roleRequest)
        );

        return ResponseEntity.ok().body(bodyResponse);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable("id") UUID id){
        roleService.hardDelete(id);
        return ResponseEntity.ok(new MessageResponse("role_hard_deleted_success", id.toString()));
    }

    @PatchMapping("/{roleId}/add-permission/{permissionId}")
    public ResponseEntity<BodyResponse> addPermission(
            @PathVariable("roleId") UUID roleId,
            @PathVariable("permissionId") UUID permissionId
    ){
        var bodyResponse = new BodyResponse(
            tl("role_added_permission_success",
                    permissionId.toString(),
                    roleId.toString()
                ),
            roleService.addPermission(roleId, permissionId)
        );

        return ResponseEntity.ok(bodyResponse);
    }

    @PatchMapping("/{roleId}/remove-permission/{permissionId}")
    public ResponseEntity<BodyResponse> removePermission(
            @PathVariable("roleId") UUID roleId,
            @PathVariable("permissionId") UUID permissionId
    ){
        var bodyResponse = new BodyResponse(
            tl("role_removed_permission_success",
                    permissionId.toString(),
                    roleId.toString()
            ),
            roleService.removePermission(roleId, permissionId)
        );

        return ResponseEntity.ok(bodyResponse);
    }

    @PatchMapping("/{roleId}/permissions")
    public ResponseEntity<BodyResponse> managePermissions(
            @PathVariable("roleId") UUID roleId,
            @RequestBody UUIDListRequest request
    ){
        var bodyResponse = new BodyResponse(
                tl("role_managed_success"),
                roleService.managePermission(roleId, request.getList())
        );

        return ResponseEntity.ok(bodyResponse);
    }

}
