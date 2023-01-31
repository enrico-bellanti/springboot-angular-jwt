package com.baseApp.backend.controllers;

import com.baseApp.backend.payloads.requests.PermissionRequest;
import com.baseApp.backend.payloads.responses.BodyResponse;
import com.baseApp.backend.payloads.responses.MessageResponse;
import com.baseApp.backend.services.PermissionService;
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
@RequestMapping("/api/v1/permission/")
@RequiredArgsConstructor
public class PermissionController {

    @Autowired
    PermissionService permissionService;

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
                .message(tl("permission_list_success"))
                .data(permissionService.getAll(pageRequest))
                .build();

        return ResponseEntity.ok().body(bodyResponse);

    }

    @PostMapping
    public ResponseEntity<BodyResponse> create(@Valid @RequestBody PermissionRequest permissionRequest) {

        var bodyResponse = new BodyResponse(
                tl("permission_created_success"),
                permissionService.create(permissionRequest)
        );

        return ResponseEntity.ok().body(bodyResponse);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable("id") UUID id){
        permissionService.hardDelete(id);
        return ResponseEntity.ok(new MessageResponse("role_hard_deleted_success", id.toString()));
    }

}
