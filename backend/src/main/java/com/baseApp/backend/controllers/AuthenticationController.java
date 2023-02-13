package com.baseApp.backend.controllers;

import com.baseApp.backend.exceptions.UserException;
import com.baseApp.backend.mail.templates.ActivationMail;
import com.baseApp.backend.models.Mail;
import com.baseApp.backend.models.NotificationEvent;
import com.baseApp.backend.models.UserDetailsImpl;
import com.baseApp.backend.payloads.requests.RefreshTokenRequest;
import com.baseApp.backend.payloads.requests.SignInRequest;
import com.baseApp.backend.payloads.requests.SignUpRequest;
import com.baseApp.backend.payloads.requests.UpdatePasswordRequest;
import com.baseApp.backend.payloads.responses.AuthenticationResponse;
import com.baseApp.backend.payloads.responses.BodyResponse;
import com.baseApp.backend.payloads.responses.MessageResponse;
import com.baseApp.backend.services.AuthenticationService;
import com.baseApp.backend.services.BrokerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

import static com.baseApp.backend.utils.TranslateUtils.tl;

@RestController
@RequestMapping("/api/v1/auth/")
@RequiredArgsConstructor
public class AuthenticationController {

    @Autowired
    BrokerService brokerService;

    @Autowired
    private final AuthenticationService authService;

    @PostMapping("/sign-up")
    public ResponseEntity<MessageResponse> register(
            @Valid @RequestBody SignUpRequest signUpRequest
    ){
        var user = authService.register(signUpRequest);
        var activationToken = authService.generateActivationToken(user);

        var mail = new ActivationMail(user, activationToken);
        var event = new NotificationEvent(
                "type-test",
                user.getId(),
                mail
        );

        brokerService.notifyToKafka("email-topic", event);

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

    @PostMapping("/update-password")
    public ResponseEntity<MessageResponse> updatePassword(
            @Valid @RequestBody UpdatePasswordRequest updatePasswordRequest,
            @AuthenticationPrincipal UserDetailsImpl user
            ){
        authService.updatePassword(user.getId(), updatePasswordRequest.getPassword());
        return ResponseEntity.ok(new MessageResponse("user_password_updated"));
    }

    @GetMapping("/activate/{token}")
    public ResponseEntity<MessageResponse> activateUser(
            @PathVariable("token") String token
    ){
        authService.activateUser(token);
        return ResponseEntity.ok(new MessageResponse("user_activated"));
    }

}
