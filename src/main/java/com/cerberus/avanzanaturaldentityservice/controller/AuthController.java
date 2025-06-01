package com.cerberus.avanzanaturaldentityservice.controller;

import com.cerberus.avanzanaturaldentityservice.dto.UserDto;
import com.cerberus.avanzanaturaldentityservice.exception.NotFoundException;
import com.cerberus.avanzanaturaldentityservice.exception.ValidationException;
import com.cerberus.avanzanaturaldentityservice.model.RefreshToken;
import com.cerberus.avanzanaturaldentityservice.model.UserCredential;
import com.cerberus.avanzanaturaldentityservice.security.AuthRequest;
import com.cerberus.avanzanaturaldentityservice.security.JwtResponse;
import com.cerberus.avanzanaturaldentityservice.security.RefreshTokenRequest;
import com.cerberus.avanzanaturaldentityservice.service.AuthService;
import com.cerberus.avanzanaturaldentityservice.service.JwtService;
import com.cerberus.avanzanaturaldentityservice.service.RefreshTokenService;
import com.cerberus.avanzanaturaldentityservice.validator.CreateValidator;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Auth Controller", description = "Registration, login ang getting refresh refreshToken")
@RateLimiter(name = "authLimiter")
public class AuthController {

    private final AuthService authService;

    private final RefreshTokenService refreshTokenService;

    private final JwtService jwtService;

    private final CreateValidator createValidator;

    @PostMapping("/register")
    @Operation(summary = "Create user")
    public ResponseEntity<String> registration(@RequestBody @Valid UserDto userDto, BindingResult bindingResult) {
        this.createValidator.validate(userDto);
        if(bindingResult.hasFieldErrors()) throw new ValidationException(collectErrorsToString(bindingResult.getFieldErrors()));

        userDto.setEmailConfirmed(false); //by default
        this.authService.createUser(userDto);
        return ResponseEntity.ok("The user has been created.");
    }

    @PostMapping("/login")
    @Operation(summary = "Login")
    public JwtResponse login(@RequestBody AuthRequest authRequest) {
        UserCredential userCredential = this.authService.login(authRequest);
        return JwtResponse
                .builder()
                .accessToken(this.authService.generateToken(userCredential.getEmail()))
                .refreshToken(this.refreshTokenService.createRefreshToken(userCredential.getEmail()).getToken())
                .build();
    }

    @DeleteMapping("/delete-all-refresh-tokens")
    public ResponseEntity<String> deleteAll(@RequestParam("userId") Long userId){
        this.refreshTokenService.deleteAll(userId);
        return ResponseEntity.ok("All tokens have been deleted.");
    }

    @DeleteMapping("/logout")
    @Operation(summary = "Logout with refreshToken")
    public ResponseEntity<String> logout(@RequestBody RefreshTokenRequest refreshTokenRequest){
        this.authService.logout(refreshTokenRequest);
        return ResponseEntity.ok("Logout successful");
    }

    @PostMapping("/refresh-token")
    @Operation(summary = "Get refreshToken")
    public JwtResponse refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return this.refreshTokenService.findByToken(refreshTokenRequest.getRefreshToken())
                .map(this.refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUserCredential)
                .map(userCredential -> {
                    String accessToken = this.jwtService.generateToken(userCredential);
                    return JwtResponse.builder()
                            .accessToken(accessToken)
                            .refreshToken(refreshTokenRequest.getRefreshToken())
                            .build();
                }).orElseThrow(() -> new NotFoundException("Token not found"));
    }

    private String collectErrorsToString(List<FieldError> fieldErrors){
        return fieldErrors.stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList().toString();
    }
}
