package com.cerberus.avanzanaturaldentityservice.service.impl;

import com.cerberus.avanzanaturaldentityservice.exception.NotFoundException;
import com.cerberus.avanzanaturaldentityservice.exception.ValidationException;
import com.cerberus.avanzanaturaldentityservice.model.RefreshToken;
import com.cerberus.avanzanaturaldentityservice.repository.RefreshTokenRepository;
import com.cerberus.avanzanaturaldentityservice.repository.UserCredentialRepository;
import com.cerberus.avanzanaturaldentityservice.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    private final UserCredentialRepository userCredentialRepository;

    @Override
    public RefreshToken createRefreshToken(String email) {
        RefreshToken refreshToken = RefreshToken.builder()
                .userCredential(this.userCredentialRepository.findByEmail(email).orElseThrow(
                        () -> new NotFoundException("User with this id not found")
                ))
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusSeconds(600000))
                .build();
        return this.refreshTokenRepository.save(refreshToken);
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return this.refreshTokenRepository.findByToken(token);
    }

    @Override
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            this.refreshTokenRepository.delete(token);
            throw new ValidationException(token.getToken() +
                    "Refresh token was expired. Please make a new signin request");
        }
        return token;
    }
}
