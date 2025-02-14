package com.cerberus.avanzanaturaldentityservice.service.impl;

import com.cerberus.avanzanaturaldentityservice.model.UserCredential;
import com.cerberus.avanzanaturaldentityservice.repository.UserCredentialRepository;
import com.cerberus.avanzanaturaldentityservice.service.AuthService;
import com.cerberus.avanzanaturaldentityservice.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserCredentialRepository userCredentialRepository;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    @Override
    public String saveUser(UserCredential credential) {
        credential.setPassword(this.passwordEncoder.encode(credential.getPassword()));
        this.userCredentialRepository.save(credential);
        return "user added to the system";
    }

    @Override
    public String generateToken(String email) {
        return this.jwtService.generateToken(email);
    }

    @Override
    public void validateToken(String token) {
        this.jwtService.validateToken(token);
    }
}
