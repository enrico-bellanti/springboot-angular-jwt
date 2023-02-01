package com.baseApp.backend.services;

import com.baseApp.backend.enums.DefaultRoles;
import com.baseApp.backend.exceptions.RefreshTokenException;
import com.baseApp.backend.exceptions.RoleException;
import com.baseApp.backend.exceptions.UserException;
import com.baseApp.backend.models.RefreshToken;
import com.baseApp.backend.models.Role;
import com.baseApp.backend.models.User;
import com.baseApp.backend.models.UserDetailsImpl;
import com.baseApp.backend.seeders.payloads.requests.RefreshTokenRequest;
import com.baseApp.backend.seeders.payloads.requests.SignInRequest;
import com.baseApp.backend.seeders.payloads.requests.SignUpRequest;
import com.baseApp.backend.seeders.payloads.responses.AuthenticationResponse;
import com.baseApp.backend.repositories.RoleRepository;
import com.baseApp.backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final RoleRepository roleRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final JwtService jwtService;
    @Autowired
    private final AuthenticationManager authenticationManager;

    @Autowired
    private final RefreshTokenService refreshTokenService;


    public void register(SignUpRequest signUpRequest) {
        String email = signUpRequest.getEmail();

        if (userRepository.existsByEmail(email)) {
            throw new UserException("user_email_already_exist", email);
        }

        Role role = roleRepository.findByName(DefaultRoles.USER.name())
                .orElseThrow(() -> new RoleException("role_not_found"));

        var user = User.builder()
                .firstName(signUpRequest.getFirstName())
                .lastName(signUpRequest.getLastName())
                .email(email)
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .roles(new HashSet<>(Arrays.asList(role)))
                .build();

        userRepository.save(user);
    }

    public AuthenticationResponse authenticate(SignInRequest signInRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signInRequest.getEmail(),
                        signInRequest.getPassword()
                )
        );

        var user = userRepository.findByEmail(signInRequest.getEmail())
                .orElseThrow();
        UUID userId = user.getId();

        if (refreshTokenService.findByUserId(user).isPresent()){
            refreshTokenService.deleteByUserId(userId);
        }

        var refreshToken = refreshTokenService.createRefreshToken(userId);

        return new AuthenticationResponse(
                user.getId(),
                user.getEmail(),
                user.getRoles(),
                jwtService.generateToken(UserDetailsImpl.build(user)),
                refreshToken.getToken()
        );
    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest){
        UUID requestRefreshToken = refreshTokenRequest.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    return new AuthenticationResponse(
                            user.getId(),
                            user.getEmail(),
                            user.getRoles(),
                            jwtService.generateToken(UserDetailsImpl.build(user)),
                            requestRefreshToken
                    );
                })
                .orElseThrow(() -> new RefreshTokenException("refresh_token_not_exist"));
    }

    public void logOut(UUID userId){
        refreshTokenService.deleteByUserId(userId);
    }
}
