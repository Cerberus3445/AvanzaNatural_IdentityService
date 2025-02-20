package com.cerberus.avanzanaturaldentityservice.controller;

import com.cerberus.avanzanaturaldentityservice.dto.UserDto;
import com.cerberus.avanzanaturaldentityservice.exception.AuthorizationException;
import com.cerberus.avanzanaturaldentityservice.exception.NotFoundException;
import com.cerberus.avanzanaturaldentityservice.exception.ValidationException;
import com.cerberus.avanzanaturaldentityservice.model.RefreshToken;
import com.cerberus.avanzanaturaldentityservice.security.AuthRequest;
import com.cerberus.avanzanaturaldentityservice.security.JwtResponse;
import com.cerberus.avanzanaturaldentityservice.security.RefreshTokenRequest;
import com.cerberus.avanzanaturaldentityservice.service.AuthService;
import com.cerberus.avanzanaturaldentityservice.service.JwtService;
import com.cerberus.avanzanaturaldentityservice.service.RefreshTokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    private final RefreshTokenService refreshTokenService;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public String addNewUser(@RequestBody @Valid UserDto userDto, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            throw new ValidationException(collectErrorsToString(bindingResult.getFieldErrors()));
        }
        userDto.setEmailConfirmed(false); //by default
        return this.authService.saveUser(userDto);
    }

    @PostMapping("/login")
    public JwtResponse login(@RequestBody AuthRequest authRequest) {
        Authentication authenticate = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
        if (authenticate.isAuthenticated()) {
            return JwtResponse
                    .builder()
                    .accessToken(this.authService.generateToken(authRequest.getEmail()))
                    .refreshToken(this.refreshTokenService.createRefreshToken(authRequest.getEmail()).getToken())
                    .build();
        } else {
            throw new AuthorizationException("Invalid access");
        }
    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token) {
        this.authService.validateToken(token);
        return "Token is valid";
    }

    @PostMapping("/refresh-token")
    public JwtResponse refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return this.refreshTokenService.findByToken(refreshTokenRequest.getToken())
                .map(this.refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUserCredential)
                .map(userCredential -> {
                    String accessToken = this.jwtService.generateToken(userCredential);
                    return JwtResponse.builder()
                            .accessToken(accessToken)
                            .refreshToken(refreshTokenRequest.getToken())
                            .build();
                }).orElseThrow(() -> new NotFoundException("Token not found"));
    }

    private String collectErrorsToString(List<FieldError> fieldErrors){
        return fieldErrors.stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList().toString();
    }
}
