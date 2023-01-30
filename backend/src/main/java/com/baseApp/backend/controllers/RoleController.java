package com.baseApp.backend.controllers;

import com.baseApp.backend.payloads.responses.BodyResponse;
import com.baseApp.backend.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static com.baseApp.backend.utils.TranslateUtils.tl;

@RestController
@RequestMapping("/api/v1/role")
@RequiredArgsConstructor
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping
    public @ResponseBody ResponseEntity<BodyResponse> list(
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
}
