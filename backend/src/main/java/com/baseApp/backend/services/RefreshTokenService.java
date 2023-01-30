package com.baseApp.backend.services;

import com.baseApp.backend.exceptions.RefreshTokenException;
import com.baseApp.backend.models.RefreshToken;
import com.baseApp.backend.models.User;
import com.baseApp.backend.repositories.RefreshTokenRepository;
import com.baseApp.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {
    private final Long REFRESH_TOKEN_EXP_MS;
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserRepository userRepository;

    public RefreshTokenService(
            @Value("${authentication.jwt-refresh-exp}") Long REFRESH_TOKEN_EXP_MS
    ) {
        this.REFRESH_TOKEN_EXP_MS = REFRESH_TOKEN_EXP_MS;
    }

    public Optional<RefreshToken> findByToken(UUID token) {
        return refreshTokenRepository.findByToken(token);
    }

    public Optional<RefreshToken> findByUserId(User user) {
        return refreshTokenRepository.findByUser(user);
    }

    public RefreshToken createRefreshToken(UUID userId) {
        RefreshToken refreshToken =  RefreshToken.builder()
                .user(userRepository.findById(userId).get())
                .expiryDate(Instant.now().plusMillis(REFRESH_TOKEN_EXP_MS))
                .token(UUID.randomUUID())
                .build();

        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new RefreshTokenException("refresh_token_expired");
        }

        return token;
    }

    @Transactional
    public int deleteByUserId(UUID userId) {
        return refreshTokenRepository.deleteByUser(userRepository.findById(userId).get());
    }


}
