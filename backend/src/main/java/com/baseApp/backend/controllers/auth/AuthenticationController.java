package com.baseApp.backend.controllers.auth;

import com.baseApp.backend.payloads.requests.SignInRequest;
import com.baseApp.backend.payloads.requests.SignUpRequest;
import com.baseApp.backend.payloads.responses.AuthenticationResponse;
import com.baseApp.backend.payloads.responses.MessageResponse;
import com.baseApp.backend.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth/")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authService;

    @PostMapping("/sign-up")
    public ResponseEntity<?> register(
            @Valid @RequestBody SignUpRequest signUpRequest
    ){
        authService.register(signUpRequest);
        return ResponseEntity.ok(new MessageResponse("user_registered"));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<AuthenticationResponse> login(
            @Valid @RequestBody SignInRequest signInRequest
    ){
        return ResponseEntity.ok(authService.authenticate(signInRequest));
    }
}
