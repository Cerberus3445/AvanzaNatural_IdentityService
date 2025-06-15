package com.cerberus.avanzanaturaldentityservice.service.impl;

import com.cerberus.avanzanaturaldentityservice.exception.NotFoundException;
import com.cerberus.avanzanaturaldentityservice.exception.ValidationException;
import com.cerberus.avanzanaturaldentityservice.model.RefreshToken;
import com.cerberus.avanzanaturaldentityservice.model.UserCredential;
import com.cerberus.avanzanaturaldentityservice.repository.RefreshTokenRepository;
import com.cerberus.avanzanaturaldentityservice.service.RefreshTokenService;
import com.cerberus.avanzanaturaldentityservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    private final UserService userService;

    /**
     * Creates a new refresh token for a user identified by their email. If the user
     * has more than 6 active sessions, throws a validation exception.
     */
    @Override
    public RefreshToken createRefreshToken(String email) {
        log.info("createRefreshToken {}", email);
        UserCredential userCredential = this.userService.getByEmail(email).orElseThrow(
                () -> new NotFoundException("User with this email not found")
        );
        RefreshToken refreshToken = RefreshToken.builder()
                .userCredential(userCredential)
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusSeconds(600000))
                .build();
        if(this.refreshTokenRepository.countByUserCredential_Id(userCredential.getId()) <= 6){
            return this.refreshTokenRepository.save(refreshToken);
        } else {
            throw new ValidationException("The number of active sessions cannot exceed 6. Please log out on other devices.");
        }
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        log.info("findByToken {}", token);
        return this.refreshTokenRepository.findFirstByToken(token);
    }

    @Override
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            this.refreshTokenRepository.delete(token);
            throw new ValidationException(token.getToken() +
                    "Refresh refreshToken was expired. Please make a new signin request");
        }
        return token;
    }

    @Override
    public void delete(String token) {
        log.info("delete {}", token);
        this.refreshTokenRepository.deleteByToken(token);
    }

    @Override
    public void deleteAll(Long userId) {
        log.info("deleteAll for user with {} id", userId);
        this.refreshTokenRepository.deleteAllByUserCredential_Id(userId);
    }

}
