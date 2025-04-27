package com.cerberus.avanzanaturaldentityservice.service;


import com.cerberus.avanzanaturaldentityservice.dto.UserDto;
import com.cerberus.avanzanaturaldentityservice.model.UserCredential;
import com.cerberus.avanzanaturaldentityservice.security.AuthRequest;
import com.cerberus.avanzanaturaldentityservice.security.RefreshTokenRequest;

public interface AuthService {

    String createUser(UserDto userDto);

    String generateToken(String email);

    void logout(RefreshTokenRequest refreshToken);

    UserCredential login(AuthRequest authRequest);
}
