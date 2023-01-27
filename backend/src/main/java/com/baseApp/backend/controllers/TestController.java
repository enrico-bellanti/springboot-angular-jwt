package com.baseApp.backend.controllers;

import com.baseApp.backend.payloads.requests.SignUpRequest;
import com.baseApp.backend.payloads.responses.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/test/")
@RequiredArgsConstructor
public class TestController {

    @GetMapping("/sign-up")
    public ResponseEntity<?> simpleTest(){
        return ResponseEntity.ok().body("Test Works");
    }
}
