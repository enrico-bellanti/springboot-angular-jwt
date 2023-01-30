package com.baseApp.backend.controllers;

import com.baseApp.backend.payloads.requests.SignUpRequest;
import com.baseApp.backend.payloads.responses.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/test")
@RequiredArgsConstructor
public class TestController {

    @PreAuthorize("@authorizationService.hasAllPermissions({'simple.test', 'simple.test2', 'simple.test8'})")
    @GetMapping("/base")
    public ResponseEntity<?> simpleTest(){
        return ResponseEntity.ok().body("Test Works");
    }
}
