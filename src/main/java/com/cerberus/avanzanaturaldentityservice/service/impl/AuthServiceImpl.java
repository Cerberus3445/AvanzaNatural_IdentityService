package com.cerberus.avanzanaturaldentityservice.service.impl;

import com.cerberus.avanzanaturaldentityservice.dto.UserDto;
import com.cerberus.avanzanaturaldentityservice.exception.NotFoundException;
import com.cerberus.avanzanaturaldentityservice.exception.ValidationException;
import com.cerberus.avanzanaturaldentityservice.model.UserCredential;
import com.cerberus.avanzanaturaldentityservice.security.AuthRequest;
import com.cerberus.avanzanaturaldentityservice.security.RefreshTokenRequest;
import com.cerberus.avanzanaturaldentityservice.service.AuthService;
import com.cerberus.avanzanaturaldentityservice.service.JwtService;
import com.cerberus.avanzanaturaldentityservice.service.RefreshTokenService;
import com.cerberus.avanzanaturaldentityservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    private final RefreshTokenService refreshTokenService;

    @Override
    public String createUser(UserDto userDto) {
        userDto.setPassword(this.passwordEncoder.encode(userDto.getPassword()));
        this.userService.save(userDto);
        return "user added to the system";
    }

    @Override
    public String generateToken(String email) {
        UserCredential userCredential = this.userService.getByEmail(email).get();
        return this.jwtService.generateToken(userCredential);
    }

    @Override
    public void logout(RefreshTokenRequest refreshToken) {
        this.refreshTokenService.delete(refreshToken.getRefreshToken());
    }

    @Override
    public UserCredential login(AuthRequest authRequest) {
        UserCredential userCredential = this.userService.getByEmail(authRequest.getEmail()).orElseThrow(
                () -> new NotFoundException("User with this email not found")
        );

        if(this.passwordEncoder.matches(authRequest.getPassword(), userCredential.getPassword())){
            return userCredential;
        } else{
            throw new ValidationException("The password is incorrect");
        }
    }
}
