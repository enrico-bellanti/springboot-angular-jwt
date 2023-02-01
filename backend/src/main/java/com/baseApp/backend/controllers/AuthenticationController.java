package com.baseApp.backend.controllers;

import com.baseApp.backend.models.UserDetailsImpl;
import com.baseApp.backend.seeders.payloads.requests.RefreshTokenRequest;
import com.baseApp.backend.seeders.payloads.requests.SignInRequest;
import com.baseApp.backend.seeders.payloads.requests.SignUpRequest;
import com.baseApp.backend.seeders.payloads.responses.AuthenticationResponse;
import com.baseApp.backend.seeders.payloads.responses.MessageResponse;
import com.baseApp.backend.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth/")
@RequiredArgsConstructor
public class AuthenticationController {

    @Autowired
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

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthenticationResponse> refreshToken(
            @Valid @RequestBody RefreshTokenRequest refreshTokenRequest
    ) {
        return ResponseEntity.ok(authService.refreshToken(refreshTokenRequest));
    }

    @DeleteMapping("/sign-out")
    public ResponseEntity<MessageResponse> logOut(
            @AuthenticationPrincipal UserDetailsImpl user
    ) {
        authService.logOut(user.getId());
        return ResponseEntity.ok(new MessageResponse("user_logged_out"));
    }
}
