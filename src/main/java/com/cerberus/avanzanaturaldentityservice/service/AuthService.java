package com.cerberus.avanzanaturaldentityservice.service;


import com.cerberus.avanzanaturaldentityservice.dto.UserDto;
import com.cerberus.avanzanaturaldentityservice.security.RefreshTokenRequest;

public interface AuthService {

    String createUser(UserDto userDto);

    String generateToken(String email);

    void validateToken(String token);

    void logout(RefreshTokenRequest refreshToken);
}
