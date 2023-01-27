package com.baseApp.backend.services;

import com.baseApp.backend.models.Role;
import com.baseApp.backend.models.User;
import com.baseApp.backend.models.UserDetailsImpl;
import com.baseApp.backend.payloads.requests.SignInRequest;
import com.baseApp.backend.payloads.requests.SignUpRequest;
import com.baseApp.backend.payloads.responses.AuthenticationResponse;
import com.baseApp.backend.repositories.RoleRepository;
import com.baseApp.backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public void register(SignUpRequest signUpRequest) {
        //todo make a custom RoleException
        Role role = roleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("Role not found"));

        var user = User.builder()
                .firstName(signUpRequest.getFirstName())
                .lastName(signUpRequest.getLastName())
                .email(signUpRequest.getEmail())
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

        var jwtToken = jwtService.generateToken(UserDetailsImpl.build(user));

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
