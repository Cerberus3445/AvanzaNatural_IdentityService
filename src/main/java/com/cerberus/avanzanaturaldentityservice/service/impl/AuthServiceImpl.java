package com.cerberus.avanzanaturaldentityservice.service.impl;

import com.cerberus.avanzanaturaldentityservice.dto.UserDto;
import com.cerberus.avanzanaturaldentityservice.model.UserCredential;
import com.cerberus.avanzanaturaldentityservice.service.AuthService;
import com.cerberus.avanzanaturaldentityservice.service.JwtService;
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

    @Override
    public String saveUser(UserDto userDto) {
        userDto.setPassword(this.passwordEncoder.encode(userDto.getPassword()));
        this.userService.save(userDto);
        return "user added to the system";
    }

    @Override
    public String generateToken(String email) {
        UserCredential userCredential = this.userService.findByEmail(email);
        return this.jwtService.generateToken(userCredential);
    }

    @Override
    public void validateToken(String token) {
        this.jwtService.validateToken(token);
    }
}
